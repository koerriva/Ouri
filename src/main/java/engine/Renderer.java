package engine;

import engine.graph.Mesh;
import engine.graph.ShaderProgram;
import engine.graph.Transformation;
import engine.scene.Node;
import engine.scene.Scene;
import org.joml.Matrix4d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;
import utils.ResourceLoader;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {
    private ShaderProgram shaderProgram;

    private float FOV = (float) Math.toRadians(60.0f);
    private float Z_FAR = 1000.0f;
    private float Z_NEAR = 0.01f;
    private float aspect = 16.0f/9.0f;

    private final Transformation transformation;

    public Renderer() {
        transformation = new Transformation();
    }

    public void init() throws Exception{
        glEnable(GL_DEPTH_TEST|GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
        String[] source = ResourceLoader.loadShaderFile("base");
        shaderProgram = new ShaderProgram(source[0],source[1]);

        shaderProgram.createUniform("P");
        shaderProgram.createUniform("W");
        shaderProgram.createUniform("V");
        shaderProgram.createUniform("texture0");
        shaderProgram.createUniform("time");
    }

    public void render(Window window, Scene scene){
        clear();

        if(window.isResized()){
            int width=window.getWidth(),height=window.getHeight();
            glViewport(0, 0, width, height);
            window.setResized(false);
        }

        shaderProgram.bind();
//        shaderProgram.setUniform("P",transformation.getProjectionMatrix(FOV,aspect,Z_NEAR,Z_FAR));
        shaderProgram.setUniform("P",transformation.getProjectionMatrix(10,Z_NEAR,Z_FAR));
        shaderProgram.setUniform("V",transformation.getViewMatrix(scene.getCamera()));
        shaderProgram.setUniform("time", (float) window.getTime());
        scene.getModels().forEach(model -> {
            List<Mesh> meshes = model.getMeshes();
            for (Mesh mesh : meshes) {
                Vector3f offset = model.getPosition();
                Quaternionf rotation = model.getRotation();
                rotation.rotateLocalY((float) Math.toRadians(1));
                Vector3f scale = model.getScale();
                shaderProgram.setUniform("W",transformation.getWorldMatrix(offset,rotation,scale));
                shaderProgram.setUniform("texture0",0);
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
