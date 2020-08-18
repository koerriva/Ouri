package ai.nn;

import java.util.Arrays;

public class NVector {
    private final double[] data;

    public NVector(int size){
        this.data = new double[size];
    }

    public NVector(double[] data) {
        this.data = data;
    }

    public double e(int idx){
        return data[idx];
    }

    public NVector add(NVector vec){
        int size = this.data.length;
        NVector v = new NVector(size);
        for (int i = 0; i < size; i++) {
           v.data[i] = data[i]+vec.data[i];
        }
        return v;
    }

    public NVector sub(NVector vec){
        int size = this.data.length;
        NVector v = new NVector(size);
        for (int i = 0; i < size; i++) {
            v.data[i] = data[i]-vec.data[i];
        }
        return v;
    }

    public NVector mul(NVector vec){
        int size = this.data.length;
        NVector v = new NVector(size);
        for (int i = 0; i < size; i++) {
            v.data[i] = data[i]*vec.data[i];
        }
        return v;
    }

    public NVector div(NVector vec){
        int size = this.data.length;
        NVector v = new NVector(size);
        for (int i = 0; i < size; i++) {
            v.data[i] = data[i]/vec.data[i];
        }
        return v;
    }

    public NVector unit(){
        int size = this.data.length;
        NVector v = new NVector(size);
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += data[i]*data[i];
        }
        double len = Math.sqrt(sum);
        for (int i = 0; i < size; i++) {
            v.data[i] = data[i]/len;
        }

        return v;
    }

    public int len(){
        return data.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }

    public static void main(String[] args) {
        NVector a = new NVector(new double[]{1,1});
        NVector b = new NVector(new double[]{1,1});
        System.out.println("a-b="+a.sub(b));
        System.out.println("a+b="+a.add(b));
        System.out.println("unit a"+a.unit());
        System.out.println("unit b"+b.unit());
    }
}
