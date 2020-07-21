package engine.scene.gltf;

public class GLTF_Material {
    private String name;
    private GLTF_MaterialPBR pbrMetallicRoughness = new GLTF_MaterialPBR();
    private float[] emissiveFactor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GLTF_MaterialPBR getPbrMetallicRoughness() {
        return pbrMetallicRoughness;
    }

    public void setPbrMetallicRoughness(GLTF_MaterialPBR pbrMetallicRoughness) {
        this.pbrMetallicRoughness = pbrMetallicRoughness;
    }

    public float[] getEmissiveFactor() {
        if(emissiveFactor==null){
            return new float[]{0,0,0};
        }
        return emissiveFactor;
    }

    public void setEmissiveFactor(float[] emissiveFactor) {
        this.emissiveFactor = emissiveFactor;
    }
}
