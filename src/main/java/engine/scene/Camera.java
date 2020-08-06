package engine.scene;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Camera {
    private final Vector3f position;
    private final Vector3f rotation;
    private final Vector3f direction = new Vector3f();

    public Camera(){
        position = new Vector3f(0);
        rotation = new Vector3f(0);
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void setPosition(float x,float y,float z){
        this.position.set(x,y,z);
    }

    public void setPosition(Vector3f position){
        this.position.set(position);
    }

    public final Vector3f getPosition(){
        return position;
    }

    public void setRotation(float x,float y,float z){
        rotation.set(x,y,z);
    }

    public void setRotation(Quaternionf rotation){
        Vector3f angle = new Vector3f();
        rotation.getEulerAnglesXYZ(angle);
        this.rotation.set(angle.x,angle.y,angle.z);
    }

    public void setRotation(Vector3f rotation){
        this.rotation.set(rotation);
    }

    public final Vector3f getRotation(){
        return rotation;
    }

    public void pitch(float offset){
        rotation.x += offset;
        direction.y = (float) Math.sin(rotation.x);
        direction.z = (float) (Math.cos(rotation.y)*Math.cos(rotation.x)*-1.0f);
        direction.x = (float) (Math.sin(rotation.y)*Math.cos(rotation.x));
    }

    public void yaw(float offset){
        rotation.y += offset;
        direction.y = (float) Math.sin(rotation.x);
        direction.z = (float) (Math.cos(rotation.y)*Math.cos(rotation.x)*-1.0f);
        direction.x = (float) (Math.sin(rotation.y)*Math.cos(rotation.x));
    }

    public void roll(float offset){
        rotation.z += offset;
    }

    public void move(float forward,float left){
        Vector3f velocity = new Vector3f();
        direction.get(velocity);
        if(forward!=0){
            velocity.x *= forward;
            velocity.z *= forward;
            velocity.y *= -forward;
        }

        if(left!=0){
            velocity.rotateY((float) Math.toRadians(90));
            velocity.x *= left;
            velocity.z *= left;
            velocity.y *= 0;
        }

        position.add(velocity);
    }
}
