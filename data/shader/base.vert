#version 410

layout (location=0) in vec3 position;
layout (location=1) in vec3 color;

out vec3 vertexColor;

void main(){
    gl_Position = vec4(position,1.0f);
    vertexColor = vec3(0.9,0.9,0.9);
}
