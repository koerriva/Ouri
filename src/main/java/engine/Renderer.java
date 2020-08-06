package engine;

import engine.graph.Material;
import engine.graph.Mesh;
import engine.graph.ShaderProgram;
import engine.graph.Transformation;
import engine.scene.Camera;
import engine.scene.Scene;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import utils.ResourceLoader;

import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Renderer {
    private ShaderProgram shaderProgram;

    private float FOV = (float) Math.toRadians(60);
    private float Z_FAR = 1000.0f;
    private float Z_NEAR = 0.01f;
    private float aspect = 16.0f/9.0f;

    private final Transformation transformation;

    public Renderer() {
        transformation = new Transformation();
    }

    public void init() throws Exception{
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
//        glEnable(GL_FRAMEBUFFER_SRGB);
//        String[] source = ResourceLoader.loadShaderFile("base");
        String[] source = ResourceLoader.loadShaderFile("pbr");
        shaderProgram = new ShaderProgram(source[0],source[1]);

        shaderProgram.createUniform("P");
        shaderProgram.createUniform("V");
        shaderProgram.createUniform("M");
//        shaderProgram.createUniform("time");

//        shaderProgram.createUniform("texture_diffuse");

        //pbr
        shaderProgram.createUniform("albedo");
        shaderProgram.createUniform("metallic");
        shaderProgram.createUniform("roughness");
        shaderProgram.createUniform("ao");
        shaderProgram.createUniform("camPos");
        shaderProgram.createUniform("lightPositions",1);
        shaderProgram.createUniform("lightColors",1);
    }

    public void render(Window window, Scene scene){
        clear();

        if(window.isResized()){
            int width=window.getWidth(),height=window.getHeight();
            glViewport(0, 0, width, height);
            window.setResized(false);
        }

        shaderProgram.bind();
        shaderProgram.setUniform("P",transformation.getProjectionMatrix(FOV,aspect,Z_NEAR,Z_FAR));
        shaderProgram.setUniform("V",scene.getCamera().getViewMatrix());
        scene.getModels().forEach(model -> {
            List<Mesh> meshes = model.getMeshes();
            for (Mesh mesh : meshes) {
                shaderProgram.setUniform("M",transformation.getWorldMatrix(model));
                Material mat = mesh.getMaterial();
                shaderProgram.setUniform("albedo",mat.getAlbedo());
                shaderProgram.setUniform("metallic",mat.getMetallic());
                shaderProgram.setUniform("roughness",mat.getRoughness());
                shaderProgram.setUniform("ao",0f);
                shaderProgram.setUniform("camPos",scene.getCamera().getPosition());

                shaderProgram.setUniform("lightPositions",new Vector3f[]{new Vector3f(0,10,0)});
                shaderProgram.setUniform("lightColors",new Vector3f[]{new Vector3f(100f)});
                mesh.draw();
            }
        });
        shaderProgram.unbind();
    }

    public void cleanup(){
        shaderProgram.cleanup();
    }

    public void clearColor(float red,float green,float blue,float alpha){
        glClearColor(red,green,blue,alpha);
    }

    public void clear(){
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
    }
}
