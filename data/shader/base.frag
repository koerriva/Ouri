#version 410
out vec4 outColor;

in vec3 vertexPos;
in vec3 vertexNormal;
in vec2 vertexTexCoord;

vec3 lightPos = vec3(7.0,4.0,6.0);

uniform sampler2D texture0;
uniform float time;
uniform mat4 V;
uniform mat4 W;

vec3 lightColor = vec3(1.0,1.0,1.0);

vec4 lightDir = vec4(0.0,-0.5,-0.5,0.0);

void main(){
    vec4 vertexColor = texture(texture0,vertexTexCoord);

    float intensity = max(sin(time),0);

    lightDir.y = sin(time);
    lightDir.z = -abs(1.0-abs(lightDir.y));

    vec3 A = vec3(0.0,0.0,0.0);
    vec3 D = vec3(0.0);
    vec3 S = vec3(0.0);

//    vec3 toLightDir = vertexPos - lightPos;
    vec3 toLightDir = -lightDir.xyz;
    float diffuseFactor = max(dot(vertexNormal,normalize(toLightDir)),0.0);
    D = lightColor * vertexColor.xyz * diffuseFactor * 1.0;

    outColor = vec4((A+D+S) * vertexColor.rgb,vertexColor.a) ;
}