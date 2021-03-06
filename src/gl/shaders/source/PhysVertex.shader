#version 420

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texcoord;
layout(location = 2) in vec3 normal;

out vec2 Texcoord;
out vec3 Normal;
out vec3 FragPos;
out vec4 FragPosLightSpace;

uniform mat4 model;
uniform mat4 view;
uniform mat4 proj;
uniform mat4 lightView;
uniform mat4 lightProj;

void main()
{
    Texcoord = texcoord;
    Normal = mat3(transpose(inverse(model))) * normal;  
    FragPos = vec3(model * vec4(position, 1.0f));
    gl_Position = proj * view * model * vec4(position, 1.0);
    FragPosLightSpace = (lightProj * lightView) * vec4(FragPos, 1.0);
}