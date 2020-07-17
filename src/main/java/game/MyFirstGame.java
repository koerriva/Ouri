package game;

import engine.IGameLogic;
import engine.Renderer;
import engine.Window;

import static org.lwjgl.glfw.GLFW.*;

public class MyFirstGame implements IGameLogic {
    private Renderer renderer;

    private float color;

    public MyFirstGame() {
        this.renderer = new Renderer();
    }

    @Override
    public void init() throws Exception {
        renderer.init();
    }

    @Override
    public void input(Window window) {
        if(window.isKeyPressed(GLFW_KEY_ESCAPE)){
            window.isShouldClose();
        }
    }

    @Override
    public void update(float interval) {
//        color = 0.2f;
    }

    @Override
    public void render(Window window) {
        if(window.isResized()){
            renderer.viewport(0,0,window.getWidth(),window.getHeight());
            window.setResized(false);
        }
        float r = (float) Math.abs(Math.sin(window.getTime()));
        float g = (float) Math.abs(Math.cos(window.getTime()));
        float b = 0.1f;
        renderer.clearColor(r,g,b,1.0f);
        renderer.clear();
    }
}
