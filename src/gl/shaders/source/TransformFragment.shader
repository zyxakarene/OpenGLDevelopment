#version 420

in vec2 Texcoord;
in vec3 Normal;
in vec3 FragPos;
in vec4 FragPosLightSpace;

out vec4 outColor;

layout(binding=0) uniform sampler2D tex;
layout(binding=1) uniform sampler2D shadowMap;
uniform vec3 lightColor;
uniform vec3 lightDirection;
uniform vec3 overlayColor;

uniform vec3 viewPos;

float ShadowCalculation(vec4 fragPosLightSpace)
{
    // perform perspective divide
    vec3 projCoords = fragPosLightSpace.xyz / fragPosLightSpace.w;
    // Transform to [0,1] range
    projCoords = projCoords * 0.5 + 0.5;
    // Get closest depth value from light's perspective (using [0,1] range fragPosLight as coords)
    float closestDepth = texture(shadowMap, projCoords.xy).r; 
    // Get depth of current fragment from light's perspective
    float currentDepth = projCoords.z;
    // Check whether current frag pos is in shadow
    float shadow = currentDepth > closestDepth  ? 1.0 : 0.0;
    // Keep the shadow at 0.0 when outside the far_plane region of the light's frustum.

    
    
    float bias = 0.0025f;
    shadow = currentDepth - bias > closestDepth  ? 1.0 : 0.0;  

    if(projCoords.z < 0.0 || projCoords.x < 0.0 || projCoords.y < 0.0 ||
       projCoords.z > 1.0 || projCoords.x > 1.0 || projCoords.y > 1.0)
      {
        shadow = 0.0;
      }

      if(shadow > 0)
      {
        vec2 texelSize = 1.0 / textureSize(shadowMap, 0);
        for(int x = -1; x <= 1; ++x)
        {
            for(int y = -1; y <= 1; ++y)
            {
                float pcfDepth = texture(shadowMap, projCoords.xy + vec2(x, y) * texelSize).r; 
                shadow += currentDepth - bias > pcfDepth ? 1.0 : 0.0;        
            }    
        }
        shadow /= 9.0;
      }

    return shadow;
}

void main()
{
    vec3 norm = normalize(Normal);
    vec3 lightDir = normalize(-lightDirection);

    float specularStrength = 0.5f;
    vec3 viewDir = normalize(viewPos - FragPos);
    vec3 reflectDir = reflect(-lightDir, norm); 

    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 256);
    vec3 specular = 0.5f * spec * lightColor; //0.5f is the specular strength

    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * lightColor;

    vec3 ambient = 0.2f * lightColor; //0.1f is the ambient strenght

    vec4 textureColor = texture(tex, vec2(Texcoord.x, -Texcoord.y));
    textureColor.r = textureColor.r * lightColor.r;
    textureColor.g = textureColor.g * lightColor.g;
    textureColor.b = textureColor.b * lightColor.b;

    float shadow = ShadowCalculation(FragPosLightSpace); 
    
    vec3 result = (ambient + (1.0 - shadow) * (diffuse + specular)) *  vec3(textureColor.r, textureColor.g, textureColor.b);
    outColor = vec4(result * overlayColor, 1.0);

// vec3 result = (ambient + diffuse + specular) *  vec3(textureColor.r, textureColor.g, textureColor.b); No shadow
//outColor = vec4(vec3(gl_FragCoord.z), 1.0f); //Show debth buffer
}