package ai.nn;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class NNetwork {
    private static Random random = new Random();

    private final int[] shape;
    private int inputNodeSize = 0;
    private final int[] hiddenNodeSize;
    private int hiddenLayerSize = 0;
    private int outputNodeSize = 0;

    private final NMatrix[] weightMatrix;
    private final int[] weightSize;
    private final NMatrix[] biasMatrix;
    private final int[] biasSize;

    private final double[] weights;

    public NNetwork(int[] shape){
        this.shape = shape;
        if(shape.length<3){
            throw new IllegalArgumentException("网络无法建立");
        }
        inputNodeSize = shape[0];
        hiddenLayerSize = shape.length-2;
        hiddenNodeSize = new int[hiddenLayerSize];
        System.arraycopy(shape, 1, hiddenNodeSize, 0, hiddenLayerSize);
        outputNodeSize = shape[shape.length-1];

        weightMatrix = new NMatrix[hiddenLayerSize+1];
        biasMatrix = new NMatrix[hiddenLayerSize+1];

        weightSize = new int[hiddenLayerSize+1];
        biasSize = new int[hiddenLayerSize+1];

        int len = 0;
        for (int i = 0; i < hiddenLayerSize+1; i++) {
            //增加神经元数量就增加权重矩阵的行数(row)
            //增加网络特性就增加权重列数(col)
            int row = shape[i+1];//nodes
            int col = shape[i];//input features
            weightMatrix[i] = new NMatrix(row,col);
            weightMatrix[i].random();

            biasMatrix[i] = new NMatrix(row,1);
            biasMatrix[i].random();

            weightSize[i] = row*col;
            biasSize[i] = row;

            len += row*(col+1);
        }

        weights = new double[len];
        int pos = 0;
        for (int i = 0; i < hiddenLayerSize + 1; i++) {
            NMatrix w = weightMatrix[i];
            System.arraycopy(w.getData(),0,weights,pos,w.len());
            pos += weightSize[i];
            NMatrix b = biasMatrix[i];
            System.arraycopy(b.getData(),0,weights,pos,b.len());
            pos += biasSize[i];
        }
    }

    public double[] input(NMatrix inputs){
        NMatrix a = inputs;
        for (int i = 0; i < hiddenLayerSize+1; i++) {
            NMatrix w = weightMatrix[i];
            NMatrix b = biasMatrix[i];
            NMatrix bi = new NMatrix(w.getRow(),a.getCol());
//            System.out.println(w.getRow()+","+a.getCol());
            for (int j = 0; j < w.getRow(); j++) {
                for (int k = 0; k < a.getCol(); k++) {
                    bi.e(j,k,b.e(j));
                }
            }
            a = w.mul(a).add(bi);
            active(a);
        }
        return a.getData();
    }

    private void  active(NMatrix a){
        for (int i = 0; i < a.len(); i++) {
            a.e(i, sigmoid(a.e(i)));
        }
    }

    private double sigmoid(double a){
        return 1.0 / (1+Math.exp(-a));
    }

    public void setWeights(double[] weights){
        for (int i = 0; i < this.weights.length; i++) {
            this.weights[i] = weights[i];
        }
        int pos = 0;
        for (int i = 0; i < hiddenLayerSize+1; i++) {
            int wsize = weightSize[i];
            double[] wi = new double[wsize];
            System.arraycopy(this.weights,pos,wi,0,wsize);
            weightMatrix[i].setData(wi);
            pos +=wsize;

            int bsize = biasSize[i];
            double[] bi = new double[bsize];
            System.arraycopy(this.weights,pos,bi,0,bsize);
            biasMatrix[i].setData(bi);
            pos +=bsize;
        }
    }

    public double[] getWeights() {
        return weights;
    }

    @Override
    public String toString() {
        return "NNetwork{\n" +
                "inputNodeSize=" + inputNodeSize + "\n" +
                ", hiddenNodeSize=" + Arrays.toString(hiddenNodeSize) + "\n" +
                ", hiddenLayerSize=" + hiddenLayerSize + "\n" +
                ", outputNodeSize=" + outputNodeSize + "\n" +
                ", weightMatrix=" + Arrays.toString(weightMatrix) + "\n" +
                ", weightSize=" + Arrays.toString(weightSize) + "\n" +
                ", biasMatrix=" + Arrays.toString(biasMatrix) + "\n" +
                ", biasSize=" + Arrays.toString(biasSize) + "\n" +
                ", weights=" + Arrays.toString(weights) + "\n" +
                '}';
    }

    public static NNetwork load(String filename) throws IOException {
        File f = new File(filename);
        InputStream in = new FileInputStream(f);
        int fileSize = (int)f.length();
        byte[] buffer = new byte[fileSize];
        in.read(buffer);
        String content  = new String(buffer);
        NModel model = JSON.parseObject(content,NModel.class);
        int[] shape = model.shape;
        double[] weights = model.weights;
        NNetwork nn = new NNetwork(shape);
        nn.setWeights(weights);
        System.out.println("load... "+nn);
        return nn;
    }

    public void save(String filename) throws IOException {
        JSONObject object = new JSONObject();
        object.put("shape",shape);
        object.put("weights",weights);
        String content = object.toJSONString();
        File f = new File(filename);
        OutputStream out = new FileOutputStream(f);
        out.write(content.getBytes());
        System.out.println("save... "+this);
    }

    static class NModel{
        private int[] shape;
        private double[] weights;

        public int[] getShape() {
            return shape;
        }

        public void setShape(int[] shape) {
            this.shape = shape;
        }

        public double[] getWeights() {
            return weights;
        }

        public void setWeights(double[] weights) {
            this.weights = weights;
        }
    }
}
