package gl.shaders;

import gl.glUtils.ShaderControls;
import java.io.FileNotFoundException;

public class HudShader
{

    private static final String vertex = "HudVertex.shader";
    private static final String fragment = "HudFragment.shader";
    
    private static int vertexShader, fragmentShader;
    private static int shaderProgram;

    static void load() throws FileNotFoundException
    {
        vertexShader = ShaderControls.generateVertexShader();
        ShaderControls.createShaderFrom(vertexShader, ShaderLoader.loadFile(vertex));

        fragmentShader = ShaderControls.generateFragmentShader();
        ShaderControls.createShaderFrom(fragmentShader, ShaderLoader.loadFile(fragment));

        shaderProgram = ShaderControls.createShaderProgram(vertexShader, fragmentShader);
        ShaderControls.bindFragmentLocation(shaderProgram, 0, "outColor");

        ShaderControls.link(shaderProgram);
        ShaderControls.use(shaderProgram);
        
        int shadowMap = ShaderControls.createUniform(shaderProgram, "shadowMap");
                
        ShaderControls.setUniform1I(shadowMap, 1);
    }
    
    public static int getProgram()
    {
        return shaderProgram;
    }
    
    public static void activate()
    {
        ShaderControls.use(shaderProgram);
    }
}
