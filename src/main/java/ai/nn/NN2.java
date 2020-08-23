package ai.nn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class NN2 {
    private static Logger logger = LoggerFactory.getLogger(NN.class);
    private static Random random = new Random();

    public static void main(String[] args) throws IOException {
//        NNetwork nn = new NNetwork(new int[]{2,2,1});
//        nn.setWeights(new double[]{1.0,1.0,0.5,1.0,1.0,0.5,1.0,1.0,0.5});
//
//        NMatrix inputs  = new NMatrix(2,100);
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 100; j++) {
//                inputs.e(i,j,1.0*(i+1));
//            }
//        }
//        double[] r = nn.input(inputs);
//
//        System.out.println(r.length);
//        System.out.println(Arrays.toString(r));

//        NMatrix weights = new NMatrix(2,2);
//        for (int i = 0; i < 4; i++) {
//            weights.e(i,1.0);
//        }
//
//        NMatrix input = new NMatrix(2,2);
//        input.e(0,1.0);
//        input.e(1,1.0);
//        input.e(2,2.0);
//        input.e(3,2.0);
//
//        NMatrix bias = new NMatrix(2,2);
//        bias.e(0,0.5);
//        bias.e(1,0.5);
//        bias.e(2,0.5);
//        bias.e(3,0.5);
//
//        NMatrix output = weights.mul(input).add(bias);
//
//        System.out.println(output);//[3.5000,3.5000]

        NNetwork nn = NNetwork.load("xor.json");

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int x = i;
                int y = j;
                int a = x^y;
                double[] r = nn.input(new NMatrix(new double[]{x,y},2,1));
                System.out.println("a:"+a+",p:"+Arrays.toString(r));
            }
        }
    }
}