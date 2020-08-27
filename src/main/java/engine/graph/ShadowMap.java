package engine.graph;

import org.lwjgl.system.MemoryUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL30.*;

public class ShadowMap {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 1024;

    private final int fbo;
    private final Texture texture;

    public ShadowMap() throws Exception {
        fbo = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER,fbo);

        texture = new Texture(GL_DEPTH_COMPONENT,WIDTH,HEIGHT);
        glFramebufferTexture2D(GL_FRAMEBUFFER,GL_DEPTH_ATTACHMENT,GL_TEXTURE_2D,texture.getId(),0);

        glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);

        if(glCheckFramebufferStatus(GL_FRAMEBUFFER)!=GL_FRAMEBUFFER_COMPLETE){
            throw new Exception("创建Framebuffer失败");
        }

        glBindFramebuffer(GL_FRAMEBUFFER,0);
    }

    public int getFbo() {
        return fbo;
    }

    public final Texture getTexture() {
        return texture;
    }

    public void cleanup(){
        glDeleteFramebuffers(fbo);
        texture.cleanup();
    }

    public boolean saveToImage() {
        int width = WIDTH;
        int height = HEIGHT;
        int size = width*height;
        float[] buffer = new float[size];
        FloatBuffer pixels = MemoryUtil.memAllocFloat(size);
        System.out.println("read framebuffer ...");
        glReadPixels(0,0,width,height,GL_DEPTH_COMPONENT,GL_FLOAT,pixels);
        pixels.get(buffer);
        MemoryUtil.memFree(pixels);

        BufferedImage im = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int idx = (height - j - 1) * width + i;//flip y
                int gray = (int)(buffer[idx]*255.999);
                int rgb = 0xff;
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
}
