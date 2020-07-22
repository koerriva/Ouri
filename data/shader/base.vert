#version 410

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;

uniform mat4 P;
uniform mat4 W;
uniform mat4 V;

out vec2 vertexTexCoord;

void main(){
    gl_Position = P * V * W * vec4(position,1.0);
    vertexTexCoord = texCoord;
}
