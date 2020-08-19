package engine.graph;

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
}
