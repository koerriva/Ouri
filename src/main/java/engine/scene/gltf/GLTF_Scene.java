package engine.scene.gltf;

import java.util.List;

public class GLTF_Scene {
    private String name;
    private List<Integer> nodes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getNodes() {
        return nodes;
    }

    public void setNodes(List<Integer> nodes) {
        this.nodes = nodes;
    }
}
