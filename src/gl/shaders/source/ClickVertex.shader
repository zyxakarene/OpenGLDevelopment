#version 420

in vec3 position;
in vec2 texcoord;
in vec3 normal;

uniform mat4 model;
uniform mat4 view;
uniform mat4 proj;

void main() 
{
    gl_Position = proj * view * model * vec4(position, 1.0);
}