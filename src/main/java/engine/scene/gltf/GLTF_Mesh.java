package engine.scene.gltf;

import java.util.List;

public class GLTF_Mesh {
    private String name;
    private List<GLTF_MeshPrimitive> primitives;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GLTF_MeshPrimitive> getPrimitives() {
        return primitives;
    }

    public void setPrimitives(List<GLTF_MeshPrimitive> primitives) {
        this.primitives = primitives;
    }
}
