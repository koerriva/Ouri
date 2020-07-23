package engine.graph;

import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

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

    public Mesh(byte[] positions, int posCount,byte[] normals, int normalCount,byte[] texcoords,int texcoordCount, byte[] indices, int idxCount,Texture texture){
        this.texture = texture;
        vboList = new int[4];
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

        //法线
        ByteBuffer normal = MemoryUtil.memAlloc(normals.length);
        normal.put(normals).flip();
        int normalVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,normalVbo);
        glBufferData(GL_ARRAY_BUFFER,normal,GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1,3,GL_FLOAT,false,0,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
        vboList[1] = normalVbo;
        MemoryUtil.memFree(normal);

        //UV
        ByteBuffer texcoord = MemoryUtil.memAlloc(texcoords.length);
        texcoord.put(texcoords).flip();
        int texcoordVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,texcoordVbo);
        glBufferData(GL_ARRAY_BUFFER,texcoord,GL_STATIC_DRAW);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2,2,GL_FLOAT,false,0,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
        vboList[2] = texcoordVbo;
        MemoryUtil.memFree(texcoord);

        //索引
        ByteBuffer idx = MemoryUtil.memAlloc(indices.length);
        idx.put(indices).flip();
        int idxVbo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,idxVbo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,idx,GL_STATIC_DRAW);
        vboList[3] = idxVbo;
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
