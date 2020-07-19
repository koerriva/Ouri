package engine.scene.gltf;

public class GLTF_BufferView {
    private Integer buffer;
    private Integer byteLength;
    private Integer byteOffset;

    public Integer getBuffer() {
        return buffer;
    }

    public void setBuffer(Integer buffer) {
        this.buffer = buffer;
    }

    public Integer getByteLength() {
        return byteLength;
    }

    public void setByteLength(Integer byteLength) {
        this.byteLength = byteLength;
    }

    public Integer getByteOffset() {
        return byteOffset;
    }

    public void setByteOffset(Integer byteOffset) {
        this.byteOffset = byteOffset;
    }
}
