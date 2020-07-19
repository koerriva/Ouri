#version 410

out vec4 outColor;

in vec3 vertexColor;

void main(){
    outColor = vec4(vertexColor,1.0);
}