package ai;

import ai.ga.Agent;
import ai.ga.XOR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI2 {
    private static Logger logger = LoggerFactory.getLogger(AI2.class);
    private static Random random = new Random();

    public static void main(String[] args) {
        //初始化种群
        int size = 1000;
        double mutationRate = 0.01;
        List<Agent> population = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            population.add(new XOR());
        }

        int gen = 0;
        Agent best = population.get(0);
        logger.info("Best : {},{}",best,best.getFitness());
        //选择
        while (best.getFitness()<0.90){
            gen++;
            //生成父代
            List<Agent> matingPool = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Agent parent = population.get(i);
                int n = (int)(parent.getFitness()*100);
                for (int j = 0; j < n; j++) {
                    matingPool.add(parent);
                }
//                logger.info("P {},{}",parent,parent.getFitness());
            }

            //生成子代
            for (int i = 0; i < size; i++) {
                int a = random.nextInt(matingPool.size());
                int b = random.nextInt(matingPool.size());
//                while (b==a){
//                    b = random.nextInt(matingPool.size());
//                }
                Agent parentA = matingPool.get(a);
                Agent parentB = matingPool.get(b);
                Agent child = parentA.crossover(parentB);
                child.mutate(mutationRate);

//                logger.info("C {},{}",child,child.getFitness());

                population.set(i,child);

                if(child.getFitness()>best.getFitness()){
                    best = child;
                }
            }

            logger.info("Gen {}, {}",gen,best.getFitness());
//            Thread.sleep(1000);
        }

        best.save();
    }
}
