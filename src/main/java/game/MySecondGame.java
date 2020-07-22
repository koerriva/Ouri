package game;

import engine.IGameLogic;
import engine.Renderer;
import engine.Window;
import engine.graph.Mesh;
import engine.scene.Scene;
import utils.ResourceLoader;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class MySecondGame implements IGameLogic {
    private final Renderer renderer;
    private Scene scene;

    public MySecondGame() {
        this.renderer = new Renderer();
    }

    @Override
    public void init() throws Exception {
        ResourceLoader.init();
        renderer.init();

        scene = ResourceLoader.loadScene("Tank");
    }

    @Override
    public void input(Window window) {
        if(window.isKeyPressed(GLFW_KEY_ESCAPE)){
            window.close();
        }
    }

    @Override
    public void update(float interval) {
    }

    @Override
    public void render(Window window) {
        renderer.render(window,scene);
    }

    @Override
    public void cleanup() {
        ResourceLoader.cleanup();
        renderer.cleanup();
        scene.cleanup();
    }
}
