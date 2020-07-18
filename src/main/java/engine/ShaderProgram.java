package engine;

import static org.lwjgl.opengl.GL41.*;

public class ShaderProgram {
    private final int programId;
    private final int vertShaderId,fragShaderId;

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
}
