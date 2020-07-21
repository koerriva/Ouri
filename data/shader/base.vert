#version 410

layout (location=0) in vec3 position;
layout (location=1) in vec4 color;

uniform mat4 P;
uniform mat4 W;
uniform mat4 V;

out vec4 vertexColor;

void main(){
    gl_Position = P * V * W * vec4(position,1.0);
    vertexColor = color;
}
