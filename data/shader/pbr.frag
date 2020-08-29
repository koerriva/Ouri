#version 330 core
out vec4 fragColor;

in vec2 TexCoords;
in vec3 WorldPos;
in vec3 Normal;

// material parameters
uniform vec3  albedo;
uniform float metallic;
uniform float roughness;
uniform float ao;

// lights
uniform vec3 lightPositions[1];
uniform vec3 lightDirections[1];
uniform vec3 lightColors[1];

uniform vec3 camPos;

const float PI = 3.14159265359;
const float R = 0.01745329251;

uniform sampler2D shadowMap;

in vec4 LightWorldPos;

float Shadow(vec4 position){
    float shadowFactor = 1.0;
    float bias = 0.005;
    vec3 projCoords = position.xyz/position.w;
    // 从屏幕坐标变换到纹理坐标
    projCoords = projCoords * 0.5 + 0.5;
    if(projCoords.z>1.0){
        shadowFactor = 0;
    }
    if (projCoords.z - bias < texture(shadowMap, projCoords.xy).r ){
        // 当前片元不在阴影中
        shadowFactor = 0;
    }
    return 1 - shadowFactor;

//    // 执行透视除法
//    vec3 projCoords = position.xyz / position.w;
//    // 变换到[0,1]的范围
//    projCoords = projCoords * 0.5 + 0.5;
//    // 取得最近点的深度(使用[0,1]范围下的position当坐标)
//    float closestDepth = texture(shadowMap, projCoords.xy).r;
//    // 取得当前片段在光源视角下的深度
//    float currentDepth = projCoords.z;
//    // 检查当前片段是否在阴影中
//    float bias = max(0.05 * (1.0 - dot(Normal, LightDir)), 0.005);
//    float shadow = currentDepth - bias > closestDepth  ? 1.0 : 0.0;

//    return shadow;
}

float DistributionGGX(vec3 N, vec3 H, float roughness){
    float a      = roughness*roughness;
    float a2     = a*a;
    float NdotH  = max(dot(N, H), 0.0);
    float NdotH2 = NdotH*NdotH;

    float nom   = a2;
    float denom = (NdotH2 * (a2 - 1.0) + 1.0);
    denom = PI * denom * denom;

    return nom / denom;
}
float GeometrySchlickGGX(float NdotV, float roughness){
    float r = (roughness + 1.0);
    float k = (r*r) / 8.0;

    float nom   = NdotV;
    float denom = NdotV * (1.0 - k) + k;

    return nom / denom;
}
float GeometrySmith(vec3 N, vec3 V, vec3 L, float roughness){
    float NdotV = max(dot(N, V), 0.0);
    float NdotL = max(dot(N, L), 0.0);
    float ggx2  = GeometrySchlickGGX(NdotV, roughness);
    float ggx1  = GeometrySchlickGGX(NdotL, roughness);

    return ggx1 * ggx2;
}
vec3 fresnelSchlick(float cosTheta, vec3 F0)
{
    return F0 + (1.0 - F0) * pow(1.0 - cosTheta, 5.0);
}

void main()
{
    vec3 N = normalize(Normal);
    vec3 V = normalize(camPos-WorldPos);

    vec3 F0 = vec3(0.04);
    F0 = mix(F0, albedo, metallic);

    // reflectance equation
    vec3 Lo = vec3(0.0);

    for(int i = 0; i < 1; ++i)
    {
        // calculate per-light radiance
        vec3 lightPos = lightPositions[i];
        vec3 lightDir = lightDirections[i];
        vec3 lightColor = lightColors[i];

        //point light
//        vec3 L = normalize(lightPos - WorldPos);
//        vec3 H = normalize(V + L);
//        float distance    = length(lightPos - WorldPos);
//        float attenuation = 1.0 / (distance * distance);
//        vec3 radiance     = lightColor * attenuation;

        //directional light
        vec3 L = normalize(-lightDir);
        vec3 H = normalize(V + L);
        float attenuation = 0.01;
        vec3 radiance     = lightColor * attenuation;

        // cook-torrance brdf
        float NDF = DistributionGGX(N, H, roughness);
        float G   = GeometrySmith(N, V, L, roughness);
        vec3 F    = fresnelSchlick(max(dot(H, V), 0.0), F0);

        vec3 kS = F;
        vec3 kD = vec3(1.0) - kS;
        kD *= 1.0 - metallic;

        vec3 nominator    = NDF * G * F;
        float denominator = 1.0 * max(dot(N, V), 0.0) * max(dot(N, L), 0.0) + 0.001;
        vec3 specular     = nominator / denominator;

        // add to outgoing radiance Lo
        float NdotL = max(dot(N, L), 0.0);
        Lo += (kD * albedo / PI + specular) * radiance * NdotL;
    }

    vec3 ambient = vec3(0.03) * albedo * ao;
    vec3 color = ambient + Lo*Shadow(LightWorldPos);

    color = color / (color + vec3(1.0));
//    color = pow(color, vec3(1.0/2.2));
//    color = clamp(color + vec3(0.2) * Shadow(LightWorldPos),0,1);
    fragColor = vec4(color, 1.0);
}
