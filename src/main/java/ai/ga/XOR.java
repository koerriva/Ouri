package ai.ga;

import ai.nn.NNetwork;
import java.util.Arrays;

public class XOR extends Agent {
    private final double[] gene;
    private double fitness = 0;

    private final NNetwork nn;

    public XOR() {
        nn = new NNetwork(new int[]{2,2,1});
        this.gene = nn.getWeights();
        calcFitness();
    }

    @Override
    protected void calcFitness() {
        nn.setWeights(gene);

        double score = 0;
        int times = 100;
        for (int i = 0; i < times; i++) {
            int x = random.nextInt(2);
            int y = random.nextInt(2);

            int[] inputs = new int[]{x,y};
            int answer = x^y;
            double output = nn.input(inputs)[0];
            double error = output-answer;

            double c = Math.pow(error,2);
            if(c<0.1){
                score++;
            }
        }

        fitness = score/times;
    }

    @Override
    public float getFitness() {
        return (float) fitness;
    }

    @Override
    public Agent crossover(Agent other) {
        XOR parent = (XOR)other;
        XOR child = new XOR();
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
                gene[i] = random.nextDouble()*2.f-1.f;
            }
        }
        calcFitness();
    }

    @Override
    public void save() {
        try {
            nn.save("xor.json");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(gene);
    }
}
