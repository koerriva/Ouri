package ai.ga;

import ai.nn.NMatrix;
import ai.nn.NNetwork;
import ai.nn.NVector;

import java.util.Arrays;
import java.util.Random;

public class XOR extends Agent {
    private final Random random = new Random();
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

        NMatrix inputs  = new NMatrix(2,4);
        NVector ev = new NVector(4);

        inputs.e(0,0,0); inputs.e(1,0,0);
        ev.e(0, 0);
        inputs.e(0,1,0); inputs.e(1,1,1);
        ev.e(1, 1);
        inputs.e(0,2,1); inputs.e(1,2,0);
        ev.e(2, 1);
        inputs.e(0,3,1); inputs.e(1,3,1);
        ev.e(3, 0);

        int score = 0;
        double[] r = nn.input(inputs);
        NVector rv = new NVector(r).sub(ev);
        for (double e:rv.getData()) {
            double p = Math.abs(e);
            if(p<0.1){
                score += 3;
            }else if(p>0.1&&p<0.2){
                score += 2;
            }else if(p>0.2&&p<0.3){
                score += 1;
            }
        }

        fitness = score*1.0/12;
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
