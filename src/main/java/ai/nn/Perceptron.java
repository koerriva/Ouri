package ai.nn;

import java.util.Arrays;
import java.util.Random;

public class Perceptron {
    private float[] weights;
    float learnRate = 0.001f;

    public Perceptron(int inputSize) {
        int size = inputSize;//bias
        Random random = new Random();
        weights = new float[size];
        for (int i = 0; i < size; i++) {
            weights[i] = (float)(random.nextDouble()*2.0 - 1.0);
        }
    }

    public Perceptron(float[] weights){
        this.weights = new float[weights.length];
        for (int i = 0; i < weights.length; i++) {
            this.weights[i] = weights[i];
        }
    }

    public int feedForward(int[] inputs){
        float sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += inputs[i]*weights[i];
        }

        return active(sum);
    }

    public void backForward(int[] inputs,int error){
        for (int i = 0; i < weights.length; i++) {
            float deltaWeight = inputs[i]*error*learnRate;
            weights[i] += deltaWeight;
        }
    }

    private int active(float sum){
        if(sum>0) {
            return 1;
        }
        return -1;
    }

    private void unit(){
        double a = 0;
        for (int i = 0; i < weights.length; i++) {
            a +=weights[i]*weights[i];
        }
        double len = Math.sqrt(a);
        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i]/(float) len;
        }
    }

    @Override
    public String toString() {
//        unit();
        return "" +Arrays.toString(weights) + "";
    }
}
