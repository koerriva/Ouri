package utils;

public class Timer {
    private double lastLoopTime;

    public void init(){
        lastLoopTime = getTime();
    }

    public double getTime() {
        return System.nanoTime()/1000_000_000.0;
    }

    public float getElapsedTime(){
        double time = getTime();
        double t = time - lastLoopTime;
        lastLoopTime = time;
        return (float) t;
    }

    public double getLastLoopTime(){
        return lastLoopTime;
    }
}
