package engine.graph.lights;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class DirectionalLight {
    private final Vector3f direction = new Vector3f();
    private final Quaternionf rotation = new Quaternionf();
    private final Vector3f color = new Vector3f(1);
    private final Vector3f position = new Vector3f(0,0,0);
    private final float intensity = 300f;

    public DirectionalLight(Quaternionf rotation,Vector3f translation){
        translation.get(position);
        rotation.get(this.rotation);
        direction.x = 0;
        direction.y = -0.4f;
        direction.z = -0.6f;
//        rotation.transform(new Vector3f(0,0,-1),direction);
    }

    public Vector3f getDirection() {
        return direction;
    }

    public Vector3f getColor() {
        Vector3f finalColor = new Vector3f();
        color.mul(intensity,finalColor);
        return finalColor;
    }

    public float getIntensity() {
        return intensity;
    }

    public final Quaternionf getRotation() {
        return rotation;
    }

    public final Vector3f getPosition() {
        return position;
    }
}
