package ai.nn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class NN {
    private static Logger logger = LoggerFactory.getLogger(NN.class);
    private static Random random = new Random();

    public static void main(String[] args) {
        Perceptron p = new Perceptron(3);
        logger.info("训练...{}",p);

        int times = 5000;
        int score = 0;
        for (int i = 0; i < times; i++) {
            int x = random.nextInt(500)*2-500;
            int y = random.nextInt(500)*2-500;

            int[] inputs = new int[]{x,y,1};
            int answer = line(x,y);
            int r = p.feedForward(inputs);
            int error = answer-r;
            p.backForward(inputs,error);
            if(error==0)score++;
//            logger.info("times {},score {}",i+1,score*1.0f/10000.0f);
        }

        logger.info("正确率:{}%",(score*1.0f/times)*100);
        logger.info("测试...{}",p);//[-10.132342, 10.308462, -0.63389]

        score = 0;
        times = 1000;
        for (int i = 0; i < times; i++) {
            int x = random.nextInt(1000)*2-1000;
            int y = random.nextInt(1000)*2-1000;

            int[] inputs = new int[]{x,y,1};
            int answer = line(x,y);
            int r = p.feedForward(inputs);
            if(answer==r){
                score++;
            }else{
                logger.info("预测错误 {},{} AI:{},答案:{}",x,y,r,answer);
            }
        }
        logger.info("正确率:{}%",(score*1.0f/times)*100);
    }

    public static int line(int x,int y){
        int cy = 2*x+1;
        if(cy>y)return -1;
        return 1;
    }
}
