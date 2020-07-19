#version 410

out vec4 outColor;

in vec3 outPos;

void main(){
    outColor = vec4((outPos.x+1.0)/2,outPos.y,1.0,1.0);
}