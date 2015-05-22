#version 420

out vec4 outColor;

uniform vec3 clickColor;

void main()
{
    outColor = vec4(clickColor, 1.0);
}