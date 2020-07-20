package engine.scene.gltf;

public class GLTF_Node {
    private String name;
    private Integer mesh;

    private float[] translation;
    private float[] rotation;
    private float[] scale;

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
        if(translation==null){
            return new float[]{0f,0f,0f};
        }
        return translation;
    }

    public void setTranslation(float[] translation) {
        this.translation = translation;
    }

    public float[] getRotation() {
        if(rotation==null){
            return new float[]{0f,0f,0f,0f};
        }
        return rotation;
    }

    public void setRotation(float[] rotation) {
        this.rotation = rotation;
    }

    public float[] getScale() {
        if(scale==null){
            return new float[]{1f,1f,1f};
        }
        return scale;
    }

    public void setScale(float[] scale) {
        this.scale = scale;
    }
}
