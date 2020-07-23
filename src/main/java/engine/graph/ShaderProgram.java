package engine.graph;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL41.*;

public class ShaderProgram {
    private final int programId;
    private final int vertShaderId,fragShaderId;

    private final Map<String,Integer> uniforms = new HashMap<>();

    public ShaderProgram(String vertCode,String fragCode) throws Exception {
        programId = glCreateProgram();
        if(programId==0){
            throw new Exception("create program fail!");
        }
        vertShaderId = createShader(vertCode,GL_VERTEX_SHADER);
        fragShaderId = createShader(fragCode,GL_FRAGMENT_SHADER);
        link();
    }

    private int createShader(String code,int type) throws Exception {
        int id = glCreateShader(type);
        if(id==0){
            throw new Exception("create shader " + type +" fail!");
        }
        glShaderSource(id,code);
        glCompileShader(id);
        if(glGetShaderi(id,GL_COMPILE_STATUS)==0){
            throw new Exception("compile shader "+glGetShaderInfoLog(id,1024));
        }
        glAttachShader(programId,id);
        return id;
    }

    private void link() throws Exception {
        glLinkProgram(programId);
        if(glGetProgrami(programId,GL_LINK_STATUS)==0){
            throw new Exception("link program "+glGetProgramInfoLog(programId,1024));
        }
        if(vertShaderId!=0)glDetachShader(programId,vertShaderId);
        if(fragShaderId!=0)glDetachShader(programId,fragShaderId);
        glValidateProgram(programId);
        if(glGetProgrami(programId,GL_VALIDATE_STATUS)==0){
            System.err.println("program "+glGetProgramInfoLog(programId,1024));
        }
    }

    public void bind(){
        glUseProgram(programId);
    }

    public void unbind(){
        glUseProgram(0);
    }

    public void cleanup(){
        unbind();
        if(programId!=0)glDeleteProgram(programId);
    }

    public void createUniform(String name) throws Exception {
        int location = glGetUniformLocation(programId,name);
        if(location<0){
            throw new Exception("Could not find uniform:" + name);
        }
        uniforms.put(name,location);
    }

    public void createUniform(String name,int array) throws Exception {
        for (int i = 0; i < array; i++) {
            String n = name+"["+i+"]";
            int location = glGetUniformLocation(programId,n);
            if(location<0){
                throw new Exception("Could not find uniform:" + n);
            }
            uniforms.put(n,location);
        }
    }

    public void setUniform(String name, Matrix4f value) {
        int location = uniforms.get(name);
        try (MemoryStack stack = MemoryStack.stackPush()){
            glUniformMatrix4fv(location,false,value.get(stack.mallocFloat(16)));
        }
    }

    public void setUniform(String name, float value){
        int location = uniforms.get(name);
        glUniform1f(location,value);
    }

    public void setUniform(String name, Integer value){
        int location = uniforms.get(name);
        glUniform1i(location,value);
    }

    public void setUniform(String name, Vector3f value) {
        int location = uniforms.get(name);
        try (MemoryStack stack = MemoryStack.stackPush()){
            glUniform3fv(location,value.get(stack.mallocFloat(3)));
        }
    }

    public void setUniform(String name, Vector3f[] values){
        int len = values.length;
        for (int i = 0; i < len; i++) {
            setUniform(name+"["+i+"]",values[i]);
        }
    }
}
