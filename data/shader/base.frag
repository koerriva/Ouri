#version 410
in vec2 vertexTexCoord;

uniform sampler2D texture0;

out vec4 fragColor;

void main(){
    fragColor = texture(texture0,vertexTexCoord);
}