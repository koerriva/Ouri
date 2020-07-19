package engine.scene.gltf;

import java.util.Map;

public class GLTF_MeshPrimitive {
    private Map<String,Integer> attributes;
    private Integer indices;
    private Integer material;

    public Map<String, Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Integer> attributes) {
        this.attributes = attributes;
    }

    public Integer getIndices() {
        return indices;
    }

    public void setIndices(Integer indices) {
        this.indices = indices;
    }

    public Integer getMaterial() {
        return material;
    }

    public void setMaterial(Integer material) {
        this.material = material;
    }
}
