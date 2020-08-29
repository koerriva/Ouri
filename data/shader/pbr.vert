#version 330
layout (location=0) in vec3 position;
layout (location=1) in vec3 normal;
layout (location=2) in vec2 texCoord;

uniform mat4 P;
uniform mat4 V;
uniform mat4 M;

out vec3 WorldPos;
out vec3 Normal;
out vec2 TexCoords;

uniform mat4 LP;
uniform mat4 LV;

out vec4 LightWorldPos;

void main() {
    vec4 mPos = M*vec4(position,1.0);//点的变换
    vec4 mNormal = M*vec4(normal,0.0);//方向变换
    gl_Position = P * V * mPos;

    WorldPos = mPos.xyz;
    Normal = normalize(mNormal).xyz;
    TexCoords = texCoord;

    LightWorldPos = (LP*LV*M*vec4(position,1.0));
}
