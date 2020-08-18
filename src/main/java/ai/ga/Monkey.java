package ai.ga;

public class Monkey extends Agent {
    private static String chars = "abcdefghijklmnopqrstuvwxyz ";
    private char[] gene = new char[18];//to be or not to be
    private float fitness = 0;

    public Monkey(){
        for (int i = 0; i < gene.length; i++) {
            gene[i] = chars.charAt(random.nextInt(chars.length()));
        }
        calcFitness();
    }

    @Override
    public Agent crossover(Agent other) {
        Monkey child = new Monkey();
        Monkey parent = (Monkey) other;
        //mid select
//        int mid = random.nextInt(gene.length);
//        for (int i = 0; i < gene.length; i++) {
//            if(i<mid){
//                child.gene[i] = this.gene[i];
//            }else{
//                child.gene[i] = parent.gene[i];
//            }
//        }
        //coin flipping
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
                gene[i] = chars.charAt(random.nextInt(chars.length()));
            }
        }
        calcFitness();
    }

    protected void calcFitness(){
        String target = "to be or not to be";
        int score = 0;
        for (int i = 0; i < gene.length; i++) {
            if(gene[i]==target.charAt(i)){
                score++;
            }
        }
        fitness = (float)(score*score)/(float)(target.length()*target.length());
//        fitness = (float)score/(float)target.length();
    }

    @Override
    public String toString() {
        return new String(gene);
    }

    @Override
    public float getFitness() {
        return fitness;
    }
}
