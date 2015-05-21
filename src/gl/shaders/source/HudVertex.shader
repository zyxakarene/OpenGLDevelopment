#version 420

in vec2 position;
in vec2 texcoord;

out vec2 Texcoord;

void main() 
{
    Texcoord = texcoord;
    gl_Position = vec4(position, -1.0, 1.0);
}