package engine.graph;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Material {
    private final Vector3f albedo;
    private final Texture texture;
    private float metallic = 0;
    private float roughness = 0;
    private float ao = 0;

    public Material(Vector4f albedo) {
        this.albedo = new Vector3f();
        this.albedo.x = albedo.x;
        this.albedo.y = albedo.y;
        this.albedo.z = albedo.z;
        this.texture = new Texture(albedo,2,2);
    }

    public final Vector3f getAlbedo() {
        return albedo;
    }

    public float getMetallic() {
        return metallic;
    }

    public void setMetallic(float metallic) {
        this.metallic = metallic;
    }

    public float getRoughness() {
        return roughness;
    }

    public void setRoughness(float roughness) {
        this.roughness = roughness;
    }

    public float getAo() {
        return ao;
    }

    public void setAo(float ao) {
        this.ao = ao;
    }

    public Texture getTexture() {
        return texture;
    }
}
