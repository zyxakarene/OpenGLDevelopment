#version 420

in vec2 position;
in vec2 texcoord;

out vec2 Texcoord;

uniform vec2 screenSize;

void main() 
{
    // Transform positions to [0, 1] range 
    float posX = position.x / screenSize.x;
    float posY = position.y / screenSize.y;
    vec2 newPos = vec2(posX, posY);

    // Transform positions to [-1, 1] range
    newPos = (newPos * 2) - 1;

    // Invert Y, because it is required for some reason?
    newPos.y = newPos.y * -1;

    // Pass on to fragment
    gl_Position = vec4(newPos, -1.0, 1.0);;
    Texcoord = texcoord;
}