package game;

import engine.IGameLogic;
import engine.Renderer;
import engine.Window;
import utils.ResourceLoader;

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
        ResourceLoader.init();
    }

    @Override
    public void input(Window window) {
        if(window.isKeyPressed(GLFW_KEY_ESCAPE)){
            window.close();
        }
    }

    @Override
    public void update(Window window,float interval) {
//        color = 0.2f;
    }

    @Override
    public void render(Window window) {
        float r = (float) Math.abs(Math.sin(window.getTime()));
        float g = (float) Math.abs(Math.cos(window.getTime()));
        float b = 0.1f;
        renderer.clearColor(r,g,b,1.0f);
        renderer.clear();
    }

    @Override
    public void cleanup() {
        ResourceLoader.cleanup();
    }
}
