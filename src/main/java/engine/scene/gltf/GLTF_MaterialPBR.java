package engine.scene.gltf;

public class GLTF_MaterialPBR {
    private float[] baseColorFactor = new float[]{1,1,1,1};
    private float metallicFactor = 0;
    private float roughnessFactor = 0;

    public float[] getBaseColorFactor() {
        return baseColorFactor;
    }

    public void setBaseColorFactor(float[] baseColorFactor) {
        this.baseColorFactor = baseColorFactor;
    }

    public float getMetallicFactor() {
        return metallicFactor;
    }

    public void setMetallicFactor(float metallicFactor) {
        this.metallicFactor = metallicFactor;
    }

    public float getRoughnessFactor() {
        return roughnessFactor;
    }

    public void setRoughnessFactor(float roughnessFactor) {
        this.roughnessFactor = roughnessFactor;
    }
}
