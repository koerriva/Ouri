package engine.scene.gltf;

import java.util.List;

public class GLTF {
    private int scene;
    private List<GLTF_Scene> scenes;
    private List<GLTF_Node> nodes;
    private List<GLTF_Camera> cameras;
    private List<GLTF_Material> materials;
    private List<GLTF_Mesh> meshes;
    private List<GLTF_Accessor> accessors;
    private List<GLTF_BufferView> bufferViews;
    private List<GLTF_Buffer> buffers;

    @Override
    public String toString() {
        return "GLTF{" +
                "scene=" + scene +
                ", scenes=" + scenes +
                ", nodes=" + nodes +
                ", cameras=" + cameras +
                ", materials=" + materials +
                ", meshes=" + meshes +
                ", accessors=" + accessors +
                ", bufferViews=" + bufferViews +
                ", buffers=" + buffers +
                '}';
    }

    public int getScene() {
        return scene;
    }

    public void setScene(int scene) {
        this.scene = scene;
    }

    public List<GLTF_Scene> getScenes() {
        return scenes;
    }

    public void setScenes(List<GLTF_Scene> scenes) {
        this.scenes = scenes;
    }

    public List<GLTF_Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<GLTF_Node> nodes) {
        this.nodes = nodes;
    }

    public List<GLTF_Camera> getCameras() {
        return cameras;
    }

    public void setCameras(List<GLTF_Camera> cameras) {
        this.cameras = cameras;
    }

    public List<GLTF_Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<GLTF_Material> materials) {
        this.materials = materials;
    }

    public List<GLTF_Mesh> getMeshes() {
        return meshes;
    }

    public void setMeshes(List<GLTF_Mesh> meshes) {
        this.meshes = meshes;
    }

    public List<GLTF_Accessor> getAccessors() {
        return accessors;
    }

    public void setAccessors(List<GLTF_Accessor> accessors) {
        this.accessors = accessors;
    }

    public List<GLTF_BufferView> getBufferViews() {
        return bufferViews;
    }

    public void setBufferViews(List<GLTF_BufferView> bufferViews) {
        this.bufferViews = bufferViews;
    }

    public List<GLTF_Buffer> getBuffers() {
        return buffers;
    }

    public void setBuffers(List<GLTF_Buffer> buffers) {
        this.buffers = buffers;
    }
}
