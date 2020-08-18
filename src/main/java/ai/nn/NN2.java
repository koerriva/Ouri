package ai.nn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

public class NN2 {
    private static Logger logger = LoggerFactory.getLogger(NN.class);
    private static Random random = new Random();

    public static void main(String[] args) throws IOException {
        NNetwork nn = new NNetwork(new int[]{2,2,1});
        NMatrix inputs  = new NMatrix(2,100);
        for (int i = 0; i < 200; i++) {
            double e = random.nextInt(2)*1.0f;
            inputs.e(i,e);
        }
        double[] r = nn.input(inputs);

        System.out.println(r.length);
    }
}