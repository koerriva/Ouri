#version 410

layout (location=0) in vec3 position;
layout (location=1) in vec3 normal;
layout (location=2) in vec2 texCoord;

uniform mat4 P;
uniform mat4 W;
uniform mat4 V;

out vec3 vertexPos;
out vec3 vertexNormal;
out vec2 vertexTexCoord;

void main(){
    vec4 vPos = V*W*vec4(position,1.0);
    vec4 vNormal = V*W*vec4(normal,0.0);
    gl_Position = P * vPos;

    vertexPos = vPos.xyz;
    vertexNormal = normalize(vNormal).xyz;
    vertexTexCoord = texCoord;
}
