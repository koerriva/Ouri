package engine;

import engine.graph.Mesh;
import engine.graph.ShaderProgram;
import org.joml.Matrix4d;
import org.joml.Matrix4f;
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

    private Matrix4f projectionMatrix;

    public void init() throws Exception{
        String[] source = ResourceLoader.loadShaderFile("base");
        shaderProgram = new ShaderProgram(source[0],source[1]);
        projectionMatrix = new Matrix4f().setPerspective(FOV,aspect,Z_NEAR,Z_FAR);

        shaderProgram.createUniform("P");
    }

    public void render(Window window,Mesh mesh){
        clear();

        if(window.isResized()){
            int width=window.getWidth(),height=window.getHeight();
            glViewport(0, 0, width, height);
            window.setResized(false);
        }

        shaderProgram.bind();
        shaderProgram.setUniform("P",projectionMatrix);
        mesh.draw();
        shaderProgram.unbind();
    }

    public void render(Window window, List<Mesh> meshes){
        clear();

        if(window.isResized()){
            int width=window.getWidth(),height=window.getHeight();
            glViewport(0, 0, width, height);
            window.setResized(false);
        }

        shaderProgram.bind();
        shaderProgram.setUniform("P",projectionMatrix);
        meshes.forEach(Mesh::draw);
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
