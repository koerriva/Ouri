package engine.scene;

import engine.graph.Mesh;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final String name;
    private final Vector3f position;
    private final Quaternionf rotation;
    private final Vector3f scale;

    private final List<Mesh> meshes;

    public Node(String name, List<Mesh> meshes) {
        this.name = name;
        this.meshes = new ArrayList<>(meshes);

        position = new Vector3f();
        rotation = new Quaternionf();
        scale = new Vector3f(1);
    }

    public String getName() {
        return name;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setPosition(float x,float y,float z){
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void setPosition(Vector3f translation){
        this.position.x = translation.x;
        this.position.y = translation.y;
        this.position.z = translation.z;
    }

    public void setRotation(float x,float y,float z){
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public void setRotation(Quaternionf rotation){
        this.rotation.x = rotation.x;
        this.rotation.y = rotation.y;
        this.rotation.z = rotation.z;
        this.rotation.w = rotation.w;
    }

    public void setScale(float scale){
        this.scale.x = scale;
        this.scale.y = scale;
        this.scale.z = scale;
    }

    public void setScale(Vector3f scale){
        this.scale.x = scale.x;
        this.scale.y = scale.y;
        this.scale.z = scale.z;
    }

    public final List<Mesh> getMeshes(){
        return meshes;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", rotation=" + rotation +
                ", scale=" + scale +
                ", meshes=" + meshes +
                '}';
    }
}
