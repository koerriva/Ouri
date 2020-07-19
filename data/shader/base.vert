#version 410

layout (location=0) in vec3 position;

out vec3 outPos;

void main(){
    gl_Position = vec4(position,1.0f);
    outPos = position;
}
