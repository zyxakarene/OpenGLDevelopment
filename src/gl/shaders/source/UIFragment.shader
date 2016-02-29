#version 420

in vec2 Texcoord;

out vec4 outColor;

layout(binding=0) uniform sampler2D tex;

void main()
{
    outColor = texture(tex, Texcoord);
}