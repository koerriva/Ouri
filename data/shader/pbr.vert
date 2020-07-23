#version 330
layout (location=0) in vec3 position;
layout (location=1) in vec3 normal;
layout (location=2) in vec2 texCoord;

uniform mat4 P;
uniform mat4 W;
uniform mat4 V;

out vec3 WorldPos;
out vec3 Normal;
out vec2 TexCoords;

void main() {
    vec4 vPos = V*W*vec4(position,1.0);
    vec4 vNormal = V*W*vec4(normal,0.0);
    gl_Position = P * vPos;

    WorldPos = vPos.xyz;
    Normal = normalize(vNormal).xyz;
    TexCoords = texCoord;
}
