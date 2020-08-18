import org.joml.Vector3f;

public class Test {
    public static void main(String[] args) {
        Vector3f forward = new Vector3f(1,0,-1);
        Vector3f up = new Vector3f(0,2,0);
        Vector3f r = new Vector3f();
        forward.cross(up,r);
        System.out.println(r);
        up.cross(forward,r);
        System.out.println(r);

        double a = 1.0/(1+Math.exp(-20));
        System.out.println(a);


        double b = Double.parseDouble("-9.74266650e-01");
        System.out.println(b);

        System.out.println(1^1);
    }
}
