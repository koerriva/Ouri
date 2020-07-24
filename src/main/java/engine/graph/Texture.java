package engine.graph;

import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL30.*;

public class Texture {
    private final int id;

    public Texture(Vector4f baseColor,int w,int h){
        byte[] bytes = new byte[w*h*4];
        for (int i = 0; i < w * h; i++) {
            bytes[i*4] = (byte) (baseColor.x*255.999);
            bytes[i*4+1] = (byte) (baseColor.y*255.999);
            bytes[i*4+2] = (byte) (baseColor.x*255.999);
            bytes[i*4+3] = (byte) 255;
        }
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D,id);

        ByteBuffer buffer = MemoryUtil.memAlloc(bytes.length);
        buffer.put(bytes).flip();
        glPixelStorei(GL_UNPACK_ALIGNMENT,1);
        glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,w,h,0,GL_RGBA,GL_UNSIGNED_BYTE,buffer);
        glGenerateMipmap(GL_TEXTURE_2D);
        MemoryUtil.memFree(buffer);

        System.out.println("Texture "+id);
    }

    public int getId() {
        return id;
    }
}
