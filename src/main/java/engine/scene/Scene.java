package engine.scene;

import engine.graph.Mesh;
import engine.scene.gltf.*;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Scene {
    private GLTF gltf;
    private List<Node> nodes;

    public Scene(GLTF gltf){
        this.gltf = gltf;
        nodes = new ArrayList<>(gltf.getNodes().size());

        for (GLTF_Node n:gltf.getNodes()){
            if(n.getMesh()!=null){
                String name = n.getName();
                System.out.println("Node "+name);

                Vector3f translation = new Vector3f(n.getTranslation());
                float[] r = n.getRotation();
                Quaternionf rotation = new Quaternionf(r[0],r[1],r[2],r[3]);
                Vector3f scale = new Vector3f(n.getScale());

                GLTF_Mesh m = gltf.getMeshes().get(n.getMesh());
                System.out.println("Mesh "+m.getName());

                List<Mesh> meshes = new ArrayList<>();
                for (GLTF_MeshPrimitive primitive:m.getPrimitives()) {
                    Map<String,Integer> attrs = primitive.getAttributes();
                    Integer indices = primitive.getIndices();
                    Integer material = primitive.getMaterial();

                    Integer position = attrs.get("POSITION");
                    //position
                    GLTF_Accessor posAccessor = gltf.getAccessors().get(position);
                    byte[] positionData = getBufferData(position,gltf);
                    int positionCount = posAccessor.getCount();
                    //indices
                    GLTF_Accessor indicesAccessor = gltf.getAccessors().get(indices);
                    byte[] indicesData = getBufferData(indices,gltf);
                    int indicesCount = indicesAccessor.getCount();

                    Mesh mesh = new Mesh(positionData,positionCount,indicesData,indicesCount);
                    meshes.add(mesh);
                }

                Node node = new Node(name,meshes);
                node.setPosition(translation);
                node.setScale(scale);

                nodes.add(node);
            }
        }
    }

    public List<Node> getNodes(){
        return nodes;
    }

    public void cleanup(){
        nodes.forEach(n->{
            n.getMeshes().forEach(Mesh::cleanup);
        });
    }

    private byte[] getBufferData(int idx,GLTF gltf){
        GLTF_Accessor accessor = gltf.getAccessors().get(idx);
        System.out.println("Type "+accessor.getType());
        GLTF_BufferView bufferView = gltf.getBufferViews().get(accessor.getBufferView());
        GLTF_Buffer buffer = gltf.getBuffers().get(bufferView.getBuffer());
        int start = bufferView.getByteOffset();
        int to = start+bufferView.getByteLength();
        byte[] data = Arrays.copyOfRange(buffer.getData(),start,to);
        System.out.println("except "+bufferView.getByteLength()+", actual "+data.length);
        return data;
    }
}
