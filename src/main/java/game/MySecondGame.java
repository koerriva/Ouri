package game;

import engine.IGameLogic;
import engine.Renderer;
import engine.Window;
import engine.graph.Mesh;
import engine.scene.Scene;
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
    public void update(Window window,float interval) {
        float speed = 3f;
        Vector3f dir = new Vector3f();
        if(window.isKeyPressed(GLFW_KEY_W)){
            dir.z +=-1.0f;
            System.out.println(scene.getCamera().getPosition().z);
        }
        if(window.isKeyPressed(GLFW_KEY_S)){
            dir.z +=1.0f;
            System.out.println(scene.getCamera().getPosition().z);
        }
        if(window.isKeyPressed(GLFW_KEY_A)){
            dir.x +=-1.0f;
            System.out.println(scene.getCamera().getPosition().x);
        }
        if(window.isKeyPressed(GLFW_KEY_D)){
            dir.x +=1.0f;
            System.out.println(scene.getCamera().getPosition().x);
        }
        scene.getCamera().getPosition().add(dir.mul(speed*interval));

        float r = (float) Math.toRadians(1);
        if(window.isKeyPressed(GLFW_KEY_Q)){
            scene.getCamera().getRotation().rotateLocalY(r);
        }
        if(window.isKeyPressed(GLFW_KEY_E)){
            scene.getCamera().getRotation().rotateLocalY(-r);
        }
        if(window.getMouseDirection().x<0){
            scene.getCamera().getRotation().rotateLocalY(r);
            window.setMouseDirection();
        }
        if(window.getMouseDirection().x>0){
            scene.getCamera().getRotation().rotateLocalY(-r);
            window.setMouseDirection();
        }
        if(window.getMouseDirection().y<0){
            scene.getCamera().getRotation().rotateLocalX(-r);
            window.setMouseDirection();
        }
        if(window.getMouseDirection().y>0){
            scene.getCamera().getRotation().rotateLocalX(r);
            window.setMouseDirection();
        }

        float h = 1;
        if(window.isKeyPressed(GLFW_KEY_Z)){
            scene.getCamera().getPosition().add(0,0.1f,0);
        }
        if(window.isKeyPressed(GLFW_KEY_C)){
            scene.getCamera().getPosition().add(0,-0.1f,0);
        }
//        System.out.println(window.getMouseDirection());
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
