#version 410

layout (location=0) in vec3 position;
layout (location=1) in vec3 color;

uniform mat4 P;
uniform mat4 W;

out vec3 vertexColor;

void main(){
    gl_Position = P * W * vec4(position,1.0);
    vertexColor = vec3(0.5);
}
