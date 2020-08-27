package engine.graph;

import org.joml.Vector4f;
import org.lwjgl.assimp.AIAABB;
import org.lwjgl.system.MemoryUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL30.*;

public class Texture {
    private final int id;
    private final int format;
    private final int type;
    private final int level=0;
    private final int w,h;

    public Texture(Vector4f baseColor,int w,int h){
        this.w = w;
        this.h = h;
        format = GL_RGBA;
        type = GL_UNSIGNED_BYTE;

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
        glTexImage2D(GL_TEXTURE_2D,level,format,w,h,0,format,type,buffer);
        glGenerateMipmap(GL_TEXTURE_2D);
        MemoryUtil.memFree(buffer);

        System.out.println("Texture "+id);
    }

    public Texture(int format,int w,int h){
        this.w = w;
        this.h = h;
        this.format = format;
        this.type = GL_FLOAT;

        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D,id);
        glTexImage2D(GL_TEXTURE_2D,level,format,w,h,0,format,type,0);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);

        System.out.println("Texture "+id);
    }

    public boolean saveToImage(){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(w*h);
        glGetTexImage(GL_TEXTURE_2D,level,format,type,buffer);
        float[] dst = new float[w*h];
        buffer.get(dst);
        MemoryUtil.memFree(buffer);

        BufferedImage im = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int idx = (h - j - 1) * w + i;//flip y
                int rgb = 0xff;
                int gray = (int)(dst[idx]*255.999);
                rgb = (rgb<<8)+gray;
                rgb = (rgb<<8)+gray;
                rgb = (rgb<<8)+gray;
                im.setRGB(i,j,rgb);
            }
        }
        File file = new File("screenshot/"+System.currentTimeMillis()+".png");
        try{
            ImageIO.write(im,"png",file);
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void cleanup(){
        glDeleteTextures(id);
    }
}
