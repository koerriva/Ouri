package engine.graph;

import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private final int vao;
    private final int[] vboList;
    private int indicesType = GL_UNSIGNED_INT;
    private int indicesCount = 0;
    private final Texture texture;

    public Mesh(byte[] positions, int posCount, byte[] indices, int idxCount,Texture texture){
        this.texture = texture;
        vboList = new int[3];
        indicesCount = idxCount;

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        //顶点
        ByteBuffer pos = MemoryUtil.memAlloc(positions.length);
        pos.put(positions).flip();
        int posVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,posVbo);
        glBufferData(GL_ARRAY_BUFFER,pos,GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0,3,GL_FLOAT,false,0,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
        vboList[0] = posVbo;
        MemoryUtil.memFree(pos);

        //颜色
        float[] colors = new float[posCount*2];
        for (int i=0;i<posCount;i++){
            colors[i*2]=0;
            colors[i*2+1]=0;
        }
        FloatBuffer color = MemoryUtil.memAllocFloat(colors.length);
        color.put(colors).flip();
        int colorVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,colorVbo);
        glBufferData(GL_ARRAY_BUFFER,color,GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1,2,GL_FLOAT,false,0,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
        vboList[2] = colorVbo;
        MemoryUtil.memFree(color);

        //索引
        ByteBuffer idx = MemoryUtil.memAlloc(indices.length);
        idx.put(indices).flip();
        int idxVbo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,idxVbo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,idx,GL_STATIC_DRAW);
        vboList[1] = idxVbo;
        MemoryUtil.memFree(idx);

        indicesType = GL_UNSIGNED_SHORT;

        glBindVertexArray(0);
    }

    public void draw(){
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D,texture.getId());
        glBindVertexArray(vao);
//        glDrawArrays(GL_TRIANGLES,0,getVertexCount());
        glDrawElements(GL_TRIANGLES,indicesCount,indicesType,0);
        glBindVertexArray(0);
    }

    public void cleanup(){
        glDisableVertexAttribArray(0);

        for (int vbo : vboList){
            glBindBuffer(GL_ARRAY_BUFFER,0);
            glDeleteBuffers(vbo);
        }

        glBindVertexArray(0);
        glDeleteVertexArrays(vao);
    }
}
