package engine;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private final String title;
    private int width,height;
    private long handle=0L;
    private boolean vSync = false;
    private boolean resized = false;

    private double xPos=0;
    private double yPos=0;
    private Vector2d mouseDir = new Vector2d();

    public Window(String title, int width, int height,boolean vSync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();
        if(!glfwInit()){
            throw new IllegalStateException("GLFW Init Fail!");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES,4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        handle = glfwCreateWindow(width,height,title,0L,0L);
        if(handle==0L){
            throw new RuntimeException("Create GLFW Window Fail!");
        }

        glfwSetFramebufferSizeCallback(handle,(window,w,h)->{
            this.width = w;
            this.height = h;
            this.resized = true;
        });

        double[] xPosBuff = new double[1];
        double[] yPosBuff = new double[1];
        glfwGetCursorPos(handle,xPosBuff,yPosBuff);
        xPos = xPosBuff[0]/width;yPos = yPosBuff[0]/height;
        glfwSetCursorPosCallback(handle,((window, xpos, ypos) -> {
            mouseDir.x = xpos/width - this.xPos;
            mouseDir.y = ypos/height - this.yPos;
            this.xPos = xpos/width;
            this.yPos = ypos/height;
            System.out.println(mouseDir);
        }));

//        glfwSetKeyCallback(handle,(window,key,scancode,action,mods)->{
//            if(key == GLFW_KEY_ESCAPE){
//                glfwSetWindowShouldClose(handle,true);
//            }
//        });

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if(vidMode!=null) {
            glfwSetWindowPos(handle, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
        }

        glfwMakeContextCurrent(handle);
        if(vSync){
            glfwSwapInterval(1);
        }
        glfwShowWindow(handle);

        GL.createCapabilities();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getTime(){
        return glfwGetTime();
    }

    public boolean isKeyPressed(int keyCode){
        return glfwGetKey(handle,keyCode)==GLFW_PRESS;
    }

    public boolean isShouldClose(){
        return glfwWindowShouldClose(handle);
    }

    public boolean isvSync(){
        return vSync;
    }

    public boolean isResized() {
        return resized;
    }

    public void setResized(boolean resized){
        this.resized = resized;
    }

    public void update(){
        glfwSwapBuffers(handle);
        glfwPollEvents();
    }

    public void close(){
        glfwSetWindowShouldClose(handle,true);
    }

    public Vector2d getMouseDirection(){
        return mouseDir;
    }

    public void setMouseDirection(){
        mouseDir.x = 0;
        mouseDir.y = 0;
    }
}
