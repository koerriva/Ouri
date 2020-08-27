package engine.graph.lights;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class DirectionalLight {
    private final Vector3f direction;
    private final Quaternionf rotation = new Quaternionf();
    private final Vector3f color = new Vector3f(1);
    private final Vector3f position = new Vector3f(0,0,0);
    private float intensity = 300f;

    public DirectionalLight(float xDegrees,float yDegrees, float zDegrees){
        direction = new Vector3f();
        direction.rotateX((float) Math.toRadians(xDegrees));
        direction.rotateY((float) Math.toRadians(yDegrees));
        direction.rotateZ((float) Math.toRadians(zDegrees));
    }

    public DirectionalLight(Quaternionf rotation,Vector3f translation){
        translation.get(position);
        rotation.get(this.rotation);
        direction = new Vector3f();
        rotation.getEulerAnglesXYZ(direction);
    }

    public DirectionalLight(Vector3f direction, Vector3f color) {
        this.direction = direction;
        color.get(this.color);
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
