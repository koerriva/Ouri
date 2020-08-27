package engine;

import engine.graph.*;
import engine.graph.lights.DirectionalLight;
import engine.scene.Scene;
import utils.ResourceLoader;

import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Renderer {
    private ShaderProgram meshShaderProgram;
    private ShaderProgram depthShaderProgram;

    private float FOV = (float) Math.toRadians(45);
    private float Z_FAR = 1000.0f;
    private float Z_NEAR = 0.1f;
    private float aspect = 16.0f/9.0f;

    private final Transformation transformation;

    private ShadowMap shadowMap;

    public Renderer() {
        transformation = new Transformation();
    }

    public void init() throws Exception{
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
//        glEnable(GL_FRAMEBUFFER_SRGB);

        shadowMap = new ShadowMap();
        setupDepthShaderProgram();
        setupMeshShaderProgram();
    }

    private void setupDepthShaderProgram() throws Exception{
        String[] source = ResourceLoader.loadShaderFile("depth");
        depthShaderProgram = new ShaderProgram(source[0],source[1]);

        depthShaderProgram.createUniform("P");
        depthShaderProgram.createUniform("V");
        depthShaderProgram.createUniform("M");
    }

    private void setupMeshShaderProgram() throws Exception {
        //        String[] source = ResourceLoader.loadShaderFile("base");
        String[] source = ResourceLoader.loadShaderFile("pbr");
        meshShaderProgram = new ShaderProgram(source[0],source[1]);

        meshShaderProgram.createUniform("P");
        meshShaderProgram.createUniform("V");
        meshShaderProgram.createUniform("M");
//        shaderProgram.createUniform("time");

//        shaderProgram.createUniform("texture_diffuse");

        //pbr
        meshShaderProgram.createUniform("albedo");
        meshShaderProgram.createUniform("metallic");
        meshShaderProgram.createUniform("roughness");
        meshShaderProgram.createUniform("ao");
        meshShaderProgram.createUniform("camPos");
        meshShaderProgram.createUniform("lightPositions",1);
        meshShaderProgram.createUniform("lightColors",1);
    }

    public void render(Window window, Scene scene){
        clear();

        renderDepth(window,scene);

        glViewport(0, 0, window.getWidth(), window.getHeight());

        meshShaderProgram.bind();
        meshShaderProgram.setUniform("P",transformation.getProjectionMatrix(FOV,aspect,Z_NEAR,Z_FAR));
        meshShaderProgram.setUniform("V",scene.getCamera().getViewMatrix());

        meshShaderProgram.setUniform("lightPositions",scene.getLightPositions());
        meshShaderProgram.setUniform("lightColors",scene.getLightColors());
        meshShaderProgram.setUniform("camPos",scene.getCamera().getPosition());
        scene.getModels().forEach(model -> {
            List<Mesh> meshes = model.getMeshes();
            for (Mesh mesh : meshes) {
                meshShaderProgram.setUniform("M",transformation.getWorldMatrix(model));
                Material mat = mesh.getMaterial();
                meshShaderProgram.setUniform("albedo",mat.getAlbedo());
                meshShaderProgram.setUniform("metallic",mat.getMetallic());
                meshShaderProgram.setUniform("roughness",mat.getRoughness());
                meshShaderProgram.setUniform("ao",3.0f);

                mesh.draw();
            }
        });
        meshShaderProgram.unbind();
    }

    private void renderDepth(Window window,Scene scene){
        DirectionalLight light = scene.getDirectionLights().get(0);

        glBindFramebuffer(GL_FRAMEBUFFER,shadowMap.getFbo());
        glViewport(0,0,ShadowMap.WIDTH,ShadowMap.HEIGHT);
        glClear(GL_DEPTH_BUFFER_BIT);
        depthShaderProgram.bind();
        depthShaderProgram.setUniform("P",transformation.getLightProjectionMatrix(light));
        depthShaderProgram.setUniform("V",transformation.getLightViewMatrix(light));
        scene.getModels().forEach(model -> {
            List<Mesh> meshes = model.getMeshes();
            for (Mesh mesh : meshes) {
                depthShaderProgram.setUniform("M",transformation.getWorldMatrix(model));
                mesh.draw();
            }
        });

        depthShaderProgram.unbind();
        glBindFramebuffer(GL_FRAMEBUFFER,0);
    }

    public void cleanup(){
        meshShaderProgram.cleanup();
    }

    public void clearColor(float red,float green,float blue,float alpha){
        glClearColor(red,green,blue,alpha);
    }

    public void clear(){
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
    }
}
