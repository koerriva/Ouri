package engine.scene.gltf;

import java.nio.IntBuffer;

public class GLTF_Accessor {
    private Integer bufferView;
    private Integer componentType;//5123=UNSIGNED_SHORT,5126=FLOAT,5121=UNSIGNED_BYTE
    private Integer count;
    private String type;

    public Integer getBufferView() {
        return bufferView;
    }

    public void setBufferView(Integer bufferView) {
        this.bufferView = bufferView;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getComponentType() {
        return componentType;
    }

    public void setComponentType(Integer componentType) {
        this.componentType = componentType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
