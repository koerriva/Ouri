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

    private Mesh mesh;

    private Scene scene;

    public MySecondGame() {
        this.renderer = new Renderer();
    }

    @Override
    public void init() throws Exception {
        ResourceLoader.init();
        renderer.init();

        scene = ResourceLoader.loadScene("Tank");

        //逆时针
        float[] vertices = new float[]{
                -0.5f,0.5f,0.0f,
                -0.5f,-0.5f,0.0f,
                0.5f,-0.5f,0.0f,
                0.5f,0.5f,0.0f,
        };
        int[] indices = new int[]{0,1,3,3,1,2};
        float[] colors = new float[]{
                1.0f,0.0f,0.0f,
                0.0f,1.0f,0.0f,
                0.0f,0.0f,1.0f,
                0.5f,0.5f,0.5f
        };

        mesh = new Mesh(vertices,indices,colors);
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
        renderer.render(window,scene.getNodes());
    }

    @Override
    public void cleanup() {
        ResourceLoader.cleanup();
        renderer.cleanup();
        mesh.cleanup();
        scene.cleanup();
    }
}
