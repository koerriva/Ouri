package engine.scene;

import engine.graph.Mesh;
import engine.scene.gltf.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Model {
    private GLTF gltf;
    private List<Mesh> meshList;

    public Model(GLTF gltf){
        this.gltf = gltf;
        meshList = new ArrayList<>(gltf.getMeshes().size());

        for (GLTF_Mesh m :gltf.getMeshes()) {
            System.out.println("Mesh "+m.getName());
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

                meshList.add(mesh);
            }
        }
    }

    public List<Mesh> getMeshList(){
        return meshList;
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
