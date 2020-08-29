package engine.scene;

import engine.graph.Material;
import engine.graph.Mesh;
import engine.graph.lights.DirectionalLight;
import engine.scene.gltf.*;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Scene {
    private GLTF gltf;
    private final List<Model> models;
    private Camera camera = new Camera();
    private final List<DirectionalLight> directionalLights;

    public Scene(GLTF gltf){
        this.gltf = gltf;
        models = new ArrayList<>();
        directionalLights = new ArrayList<>();

        for (GLTF_Node n:gltf.getNodes()){
            String name = n.getName();
            System.out.println("Node "+name);

            Vector3f translation = new Vector3f(n.getTranslation());
            float[] r = n.getRotation();
            Quaternionf rotation = new Quaternionf(r[0],r[1],r[2],r[3]);
            Vector3f scale = new Vector3f(n.getScale());

            List<Mesh> meshes = new ArrayList<>();
            if(n.getMesh()!=null){
                GLTF_Mesh m = gltf.getMeshes().get(n.getMesh());
                System.out.println("Mesh "+m.getName());
                for (GLTF_MeshPrimitive primitive:m.getPrimitives()) {
                    Map<String,Integer> attrs = primitive.getAttributes();
                    Integer indices = primitive.getIndices();
                    Integer materialIdx = primitive.getMaterial();

                    Integer position = attrs.get("POSITION");
                    Integer normal = attrs.get("NORMAL");
                    Integer tangent = attrs.get("TANGENT");
                    Integer texcoord = attrs.get("TEXCOORD_0");
                    //position
                    GLTF_Accessor posAccessor = gltf.getAccessors().get(position);
                    byte[] positionData = getBufferData(position,gltf);
                    int positionCount = posAccessor.getCount();
                    //normal
                    GLTF_Accessor normalAccessor = gltf.getAccessors().get(normal);
                    byte[] normalData = getBufferData(normal,gltf);
                    int normalCount = normalAccessor.getCount();
                    //tangent
                    GLTF_Accessor tangentAccessor = gltf.getAccessors().get(tangent);
                    byte[] tangentData = getBufferData(tangent,gltf);
                    int tangentCount = tangentAccessor.getCount();
                    //texcoord
                    GLTF_Accessor texcoordAccessor = gltf.getAccessors().get(texcoord);
                    byte[] texcoordData = getBufferData(texcoord,gltf);
                    int texcoordCount = texcoordAccessor.getCount();
                    //indices
                    GLTF_Accessor indicesAccessor = gltf.getAccessors().get(indices);
                    byte[] indicesData = getBufferData(indices,gltf);
                    int indicesCount = indicesAccessor.getCount();
                    //texture
                    Material material;
                    if(materialIdx!=null){
                        GLTF_Material mat = gltf.getMaterials().get(materialIdx);
                        Vector4f baseColor = new Vector4f(mat.getPbrMetallicRoughness().getBaseColorFactor());
                        material = new Material(baseColor);
                        float metallicFactor = mat.getPbrMetallicRoughness().getMetallicFactor();
                        float roughnessFactor = mat.getPbrMetallicRoughness().getRoughnessFactor();
                        material.setRoughness(roughnessFactor);
                        material.setMetallic(metallicFactor);
                    }else{
                        Vector4f baseColor = new Vector4f(1.0f);
                        material = new Material(baseColor);
                        material.setRoughness(0.99f);
                        material.setMetallic(0.01f);
                    }
                    material.setAo(1.0f);

                    Mesh mesh = new Mesh(positionData,positionCount
                            ,normalData,normalCount
                            ,texcoordData,texcoordCount
                            ,indicesData,indicesCount,material);
                    meshes.add(mesh);
                }

                Model model = new Model(name,meshes);
                model.setPosition(translation);
                model.setScale(scale);
                model.setRotation(rotation);

                models.add(model);
            }else if(name.equals("Camera")){
                camera = new Camera();
                camera.setPosition(translation);
//                camera.setRotation(rotation);
            }else if(name.equals("Light")){
                DirectionalLight light = new DirectionalLight(rotation,translation);
//                DirectionalLight light = new DirectionalLight(0,0,0);
                directionalLights.add(light);
            }
        }
    }

    public List<Model> getModels(){
        return models;
    }

    public final Camera getCamera(){
        return camera;
    }

    public final List<DirectionalLight> getDirectionLights(){
        return this.directionalLights;
    }

    public final Vector3f[] getLightPositions(){
        return directionalLights.stream().map(DirectionalLight::getPosition).toArray(Vector3f[]::new);
    }

    public final Vector3f[] getLightDirections(){
        return directionalLights.stream().map(DirectionalLight::getDirection).toArray(Vector3f[]::new);
    }

    public final Vector3f[] getLightColors(){
        return directionalLights.stream().map(DirectionalLight::getColor).toArray(Vector3f[]::new);
    }

    public void cleanup(){
        models.forEach(n->{
            n.getMeshes().forEach(Mesh::cleanup);
        });
    }

    private byte[] getBufferData(int idx,GLTF gltf){
        GLTF_Accessor accessor = gltf.getAccessors().get(idx);
//        System.out.println("Type "+accessor.getType());
        GLTF_BufferView bufferView = gltf.getBufferViews().get(accessor.getBufferView());
        GLTF_Buffer buffer = gltf.getBuffers().get(bufferView.getBuffer());
        int start = bufferView.getByteOffset();
        int to = start+bufferView.getByteLength();
        byte[] data = Arrays.copyOfRange(buffer.getData(),start,to);
//        System.out.println("except "+bufferView.getByteLength()+", actual "+data.length);
        return data;
    }
}
