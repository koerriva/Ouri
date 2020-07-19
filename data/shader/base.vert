#version 410

layout (location=0) in vec3 position;
layout (location=1) in vec3 color;

uniform mat4 P;

out vec3 vertexColor;

void main(){
    gl_Position = P * vec4(position+vec3(0.0,0.0,-10.0),1.0);
    vertexColor = vec3(0.9,0.9,0.9);
}
