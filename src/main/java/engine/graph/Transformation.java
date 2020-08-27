package engine.graph;

import engine.graph.lights.DirectionalLight;
import engine.scene.Camera;
import engine.scene.Node;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transformation {
    private final Matrix4f P;
    private final Matrix4f W;
    private final Matrix4f V;
    private final Matrix4f VM;

    public Transformation() {
        this.P = new Matrix4f();
        this.W = new Matrix4f();
        this.V = new Matrix4f();
        this.VM = new Matrix4f();
    }

    public final Matrix4f getProjectionMatrix(float fov,float aspect,float zNear,float zFar){
        return P.identity()
                .setPerspective(fov,aspect,zNear,zFar);
    }

    public final Matrix4f getProjectionMatrix(float bound,float zNear,float zFar){
        return P.identity()
                .setOrtho(-bound,bound,-bound,bound,zNear,zFar);
    }

    public Matrix4f getWorldMatrix(Vector3f offset, Quaternionf rotation, Vector3f scale){
        return W.identity()
                .translation(offset)
                .rotate(rotation)
                .scale(scale);
    }

    public Matrix4f getWorldMatrix(Node node){
        return W.identity()
                .translation(node.getPosition())
                .rotate(node.getRotation())
                .scale(node.getScale());
    }

    public final Matrix4f getViewMatrix(Camera camera){
        Vector3f pos = new Vector3f();
        pos = camera.getPosition().negate(pos);
        V.identity();
        Vector3f rot = camera.getRotation();
        V.rotate(rot.x,new Vector3f(1,0,0))
                .rotate(rot.y,new Vector3f(0,1,0))
                .rotate(rot.z,new Vector3f(0,0,1));
        V.translate(pos);
        return V;
    }

    public final Matrix4f getModelViewMatrix(Node node, Matrix4f view){
        VM.set(view).translate(node.getPosition())
                .rotate(node.getRotation())
                .scale(node.getScale());
        return VM;
    }

    public final Matrix4f getLightProjectionMatrix(DirectionalLight light) {
        return P.identity()
                .setOrtho(-50,50,-50,50,-1,100);
    }

    public final Matrix4f getLightViewMatrix(DirectionalLight light) {
        Vector3f pos = new Vector3f();
        light.getPosition().get(pos);
        V.identity();
        Vector3f rot = new Vector3f();
        light.getRotation().getEulerAnglesXYZ(rot);
        V.rotate(rot.x,new Vector3f(1,0,0))
                .rotate(rot.y,new Vector3f(0,1,0))
                .rotate(rot.z,new Vector3f(0,0,1));
        V.translate(pos);
        return V;
    }
}
