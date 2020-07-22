#version 410

layout (location=0) in vec3 position;
layout (location=1) in vec3 normal;
layout (location=2) in vec4 color;

uniform mat4 P;
uniform mat4 W;
uniform mat4 V;

out vec4 vertexColor;
out vec3 vertexNormal;
out vec3 vertexPos;

void main(){
    vec4 vPos = V*W*vec4(position,1.0);
    vec4 vNormal = V*W*vec4(normal,0.0);
    gl_Position = P * vPos;
    vertexColor = color;
    vertexNormal = normalize(vNormal).xyz;
    vertexPos = vPos.xyz;
}
