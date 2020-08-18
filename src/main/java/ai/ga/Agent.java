package ai.ga;

import java.util.Random;

public abstract class Agent {
    protected static Random random = new Random();

    protected abstract void calcFitness();

    public void save(){}

    public abstract float getFitness();

    public abstract Agent crossover(Agent other);

    public abstract void mutate(double p);

    public abstract String toString();
}
