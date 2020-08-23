package ai.ga;

import ai.nn.Perceptron;

import java.util.Arrays;
import java.util.Random;

import static ai.nn.NN.line;

public class Liner extends Agent {
    private Random random = new Random();
    private float[] gene;
    private float fitness = 0;

    public Liner() {
        gene = new float[3];
        for (int i = 0; i < 3; i++) {
            gene[i] = random.nextFloat()*2.f-1.f;
        }
        calcFitness();
    }

    @Override
    public Agent crossover(Agent other) {
        Liner parent = (Liner)other;
        Liner child = new Liner();
        for (int i = 0; i < gene.length; i++) {
            double p = random.nextDouble();
            if(p<=0.5){
                child.gene[i] = this.gene[i];
            }else{
                child.gene[i] = parent.gene[i];
            }
        }
        child.calcFitness();
        return child;
    }

    @Override
    public void mutate(double p) {
        for (int i = 0; i < gene.length; i++) {
            double mp = random.nextDouble();
            if(mp<=p){
                gene[i] = random.nextFloat()*2.f-1.f;
            }
        }
        calcFitness();
    }

    @Override
    protected void calcFitness() {
        Perceptron p = new Perceptron(gene);
        int score = 0;
        int times = 100;
        for (int i = 0; i < times; i++) {
            int x = random.nextInt(1000)*2-1000;
            int y = random.nextInt(1000)*2-1000;

            int[] inputs = new int[]{x,y,1};
            int answer = line(x,y);
            int r = p.feedForward(inputs);
            if(answer==r){
                score++;
            }
        }

        fitness = score*1.0f/times;
    }

    @Override
    public String toString() {
        return Arrays.toString(gene);
    }

    @Override
    public float getFitness() {
        return fitness;
    }
}
