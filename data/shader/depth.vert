#version 410

layout (location=0) in vec3 position;
layout (location=1) in vec3 normal;
layout (location=2) in vec2 texCoord;

uniform mat4 P;
uniform mat4 M;
uniform mat4 V;

void main() {
    gl_Position = P*V*M*vec4(position, 1.0);
}
