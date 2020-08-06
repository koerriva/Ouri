package engine.scene;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Camera {
    private final Vector3f position;
    private final Vector3f rotation;
    private final Vector3f frontAxis = new Vector3f(0,0,-1);
    private final Vector3f upAxis = new Vector3f(0,1,0);
    private final Vector3f rightAxis = new Vector3f(1,0,0);
    private final Matrix4f view = new Matrix4f();

    public Camera(){
        position = new Vector3f(0);
        rotation = new Vector3f(0);
        view.identity();
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
        frontAxis.y = (float) Math.sin(rotation.x);
        frontAxis.z = (float) (Math.cos(rotation.y)*Math.cos(rotation.x)*-1.0f);
        frontAxis.x = (float) (Math.sin(rotation.y)*Math.cos(rotation.x));

        frontAxis.cross(upAxis,rightAxis);
    }

    public void yaw(float offset){
        rotation.y += offset;
        frontAxis.y = (float) Math.sin(rotation.x);
        frontAxis.z = (float) (Math.cos(rotation.y)*Math.cos(rotation.x)*-1.0f);
        frontAxis.x = (float) (Math.sin(rotation.y)*Math.cos(rotation.x));

        frontAxis.cross(upAxis,rightAxis);
    }

    public void roll(float offset){
        rotation.z += offset;
    }

    public void move(float forward,float right){
        Vector3f velocity = new Vector3f();

        if(forward!=0){
            frontAxis.get(velocity);
            velocity.normalize();
            velocity.x *= forward;
            velocity.z *= forward;
            velocity.y *= -forward;
        }
        if(right!=0){
            rightAxis.get(velocity);
            velocity.normalize();
            velocity.x *= right;
            velocity.z *= right;
            velocity.y *= 0;
        }
        position.add(velocity);
    }

    public final Vector3f getFrontAxis(){
        return frontAxis;
    }

    public final Vector3f getUpAxis(){
        return upAxis;
    }

    public final Vector3f getRightAxis(){
        return rightAxis;
    }

    public final Matrix4f getViewMatrix(){
//        Vector3f center = new Vector3f();
//        position.add(frontAxis,center);
//        return view.identity().lookAt(position,center,upAxis);

        Vector3f pos = new Vector3f();
        pos = position.negate(pos);
        view.identity();
        Vector3f rot = rotation;
        view.rotate(rot.x,new Vector3f(1,0,0))
                .rotate(rot.y,new Vector3f(0,1,0))
                .rotate(rot.z,new Vector3f(0,0,1));
        view.translate(pos);
        return view;
    }
}
