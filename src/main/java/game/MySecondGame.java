package game;

import engine.IGameLogic;
import engine.Renderer;
import engine.Window;
import engine.graph.Mesh;
import engine.scene.Camera;
import engine.scene.Direction;
import engine.scene.Scene;
import org.joml.Vector2f;
import org.joml.Vector3f;
import utils.ResourceLoader;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class MySecondGame implements IGameLogic {
    private final Renderer renderer;
    private Scene scene;

    public MySecondGame() {
        this.renderer = new Renderer();
    }

    private Camera camera;
    @Override
    public void init() throws Exception {
        ResourceLoader.init();
        renderer.init();

        scene = ResourceLoader.loadScene("Tank");
        camera = scene.getCamera();
    }

    @Override
    public void input(Window window) {
        if(window.isKeyPressed(GLFW_KEY_ESCAPE)){
            window.close();
        }
    }

    @Override
    public void update(Window window,float interval) {
        Vector2f dir = window.getMouseDirection();
        camera.yaw((float) Math.toRadians(dir.x*interval));
        camera.pitch((float) Math.toRadians(dir.y*interval));
        if(window.isKeyPressed(GLFW_KEY_W)){
            camera.move(1,0);
        }else if(window.isKeyPressed(GLFW_KEY_S)){
            camera.move(-1,0);
        }else if(window.isKeyPressed(GLFW_KEY_A)){
            camera.move(0,1);
        }else if(window.isKeyPressed(GLFW_KEY_D)){
            camera.move(0,-1);
        }
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
