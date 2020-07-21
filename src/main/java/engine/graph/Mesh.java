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

    public Mesh(float[] positions, int[] indices, float[] colors) {
        vboList = new int[3];
        indicesCount = indices.length;

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        //顶点
        FloatBuffer pos = MemoryUtil.memAllocFloat(positions.length);
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
        FloatBuffer color = MemoryUtil.memAllocFloat(colors.length);
        color.put(colors).flip();
        int colorVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,colorVbo);
        glBufferData(GL_ARRAY_BUFFER,color,GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1,3,GL_FLOAT,false,0,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
        vboList[1] = colorVbo;
        MemoryUtil.memFree(color);

        //索引
        IntBuffer idx = MemoryUtil.memAllocInt(indices.length);
        idx.put(indices).flip();
        int idxVbo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,idxVbo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,idx,GL_STATIC_DRAW);
        vboList[2] = idxVbo;
        MemoryUtil.memFree(idx);

        glBindVertexArray(0);
    }

    public Mesh(byte[] positions, int posCount, byte[] indices, int idxCount, Vector4f baseColor){
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
        float[] colors = new float[posCount*4];
        for (int i=0;i<posCount;i++){
            colors[i*4]=baseColor.x;
            colors[i*4+1] = baseColor.y;
            colors[i*4+2] = baseColor.z;
            colors[i*4+3] = baseColor.w;
        }
        FloatBuffer color = MemoryUtil.memAllocFloat(colors.length);
        color.put(colors).flip();
        int colorVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,colorVbo);
        glBufferData(GL_ARRAY_BUFFER,color,GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1,4,GL_FLOAT,false,0,0);
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
