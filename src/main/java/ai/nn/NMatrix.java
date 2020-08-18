package ai.nn;

import org.joml.Vector3f;

import java.util.Arrays;
import java.util.Random;

public class NMatrix {
    private final double[] data;
    private final int col;
    private final int row;

    public NMatrix(double[] data,int row,int col) {
        this.data = data;
        this.col = col;
        this.row = row;
    }

    public NMatrix(int row, int col) {
        this.col = col;
        this.row = row;
        data = new double[col*row];
    }

    public double e(int r,int c){
//        System.out.println(r+"_"+c);
        return data[r*col+c];
    }

    public double e(int idx){
        return data[idx];
    }

    public double e(int r,int c,double e){
        data[r*col+c] = e;
        return e;
    }

    public double e(int idx,double e){
        data[idx]=e;
        return e;
    }

    public NMatrix mul(NMatrix other){
        NMatrix out = new NMatrix(row,other.col);

        for (int k = 0; k < other.col; k++) {
            for (int i = 0; i < row; i++) {
                double e = 0;
                for (int j = 0; j < col; j++) {
                    e += data[i*col+j]*other.e(j,k);
                }
                out.e(i,k,e);
            }
        }

        return out;
    }

    public NMatrix add(NMatrix other){
        NMatrix out = new NMatrix(row,other.col);

        for (int i = 0; i < row * col; i++) {
            out.data[i] = this.data[i]+other.data[i];
        }

        return out;
    }

    public int len(){
        return data.length;
    }

    public double[] getData() {
        return data;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                builder.append(String.format("%.16f",e(i,j)));
                if(j<col-1){
                    builder.append(" ");
                }
            }
            if(i!=row-1){
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    public static void main(String[] args) {
        NMatrix input = new NMatrix(new double[]{2,3,1},1,3);// input 3
        System.out.println(input);
        NMatrix w1 = new NMatrix(new double[]{1,1,1,1,1,1,1,1,1,1,1,1},3,4);// hidden 4
        System.out.println(w1);
        NMatrix w2 = new NMatrix(new double[]{1,1,1,1},4,1);// output 2
        NMatrix a1 = input.mul(w1);
        System.out.println(a1);
        NMatrix a2 = a1.mul(w2);
        System.out.println(a2);
    }

    public void random() {
        Random random = new Random();
        for (int i = 0; i < data.length; i++) {
            data[i]=random.nextDouble()*2-1;
        }
    }
}
