package engine.graph;

import engine.scene.Camera;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transformation {
    private final Matrix4f P;
    private final Matrix4f W;
    private final Matrix4f V;

    public Transformation() {
        this.P = new Matrix4f();
        this.W = new Matrix4f();
        this.V = new Matrix4f();
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

    public final Matrix4f getViewMatrix(Camera camera){
        Vector3f pos = new Vector3f();
        pos = camera.getPosition().negate(pos);
        return V.identity()
                .rotate(camera.getRotation())
                .translation(pos);
    }
}
