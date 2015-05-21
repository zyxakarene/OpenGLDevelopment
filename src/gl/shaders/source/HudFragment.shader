#version 420

in vec2 Texcoord;

out vec4 outColor;

uniform sampler2D shadowMap;

void main()
{
    outColor = texture(shadowMap, vec2(Texcoord.x, Texcoord.y));
}