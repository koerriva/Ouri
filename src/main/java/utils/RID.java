package utils;

public class RID {
    static long key=0;
    private RID(){}

    public static long get(){
        return key++;
    }
}
