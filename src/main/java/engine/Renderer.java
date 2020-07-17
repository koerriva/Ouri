package engine;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    public void init() throws Exception{

    }

    public void viewport(int xpos,int ypos,int width,int height){
        glViewport(xpos, ypos, width, height);
    }

    public void clearColor(float red,float green,float blue,float alpha){
        glClearColor(red,green,blue,alpha);
    }

    public void clear(){
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
    }
}
