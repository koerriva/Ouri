package engine;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class Window {
    private final String title;
    private int width,height;
    private long handle=0L;
    private boolean vSync = false;
    private boolean resized = false;

    //mouse
    private final Vector2f mouseDirection = new Vector2f();
    private final Vector2d mouseLastPosition = new Vector2d(-1);
    private final Vector2d mouseCurrPosition = new Vector2d();
    private final float mouseSpeed = 0.05f;

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

        try(MemoryStack stack = MemoryStack.stackPush()){
            DoubleBuffer xpos = stack.mallocDouble(1);
            DoubleBuffer ypos = stack.mallocDouble(1);
            glfwGetCursorPos(handle,xpos,ypos);
            double x = xpos.get();
            double y = ypos.get();
            mouseCurrPosition.set(x,y);
            mouseLastPosition.set(x,y);
        }

        glfwSetCursorPosCallback(handle,(w,xpos,ypos)->{
            mouseCurrPosition.set(xpos,ypos);
        });

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if(vidMode!=null) {
            glfwSetWindowPos(handle, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
        }

        glfwMakeContextCurrent(handle);
        if(vSync){
            glfwSwapInterval(1);
        }
        glfwShowWindow(handle);

        glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

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
        updateMouseDirection();
    }

    public void close(){
        glfwSetWindowShouldClose(handle,true);
    }

    public long getHandle() {
        return handle;
    }

    private void updateMouseDirection(){
        double xpos = mouseCurrPosition.x;
        double ypos = mouseCurrPosition.y;

        mouseDirection.x = (float) (xpos - mouseLastPosition.x);
        mouseDirection.y = (float) (ypos - mouseLastPosition.y);

        mouseLastPosition.set(xpos,ypos);
    }

    public final Vector2f getMouseDirection(){
        return mouseDirection;
    }
}
