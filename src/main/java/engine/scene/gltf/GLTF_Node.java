package engine.scene.gltf;

public class GLTF_Node {
    private String name;
    private Integer mesh;

    private float[] translation = new float[]{0f,0f,0f};
    private float[] rotation = new float[]{0f,0f,0f,1f};
    private float[] scale = new float[]{1f,1f,1f};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMesh() {
        return mesh;
    }

    public void setMesh(Integer mesh) {
        this.mesh = mesh;
    }

    public float[] getTranslation() {
        return translation;
    }

    public void setTranslation(float[] translation) {
        this.translation = translation;
    }

    public float[] getRotation() {
        return rotation;
    }

    public void setRotation(float[] rotation) {
        this.rotation = rotation;
    }

    public float[] getScale() {
        return scale;
    }

    public void setScale(float[] scale) {
        this.scale = scale;
    }
}
