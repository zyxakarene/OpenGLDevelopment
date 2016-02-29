package gl.shaders;

import gl.glUtils.ShaderControls;
import utils.constants.GameConstants;

public class HudShader extends AbstractShader
{

    private static HudShader instance;
    private int screenSize;
    private int shadowMap;

    HudShader()
    {
        instance = this;
    }
    
    @Override
    protected void setupUniforms()
    {
        screenSize = ShaderControls.createUniform(shaderProgram, "screenSize");
        shadowMap = ShaderControls.createUniform(shaderProgram, "shadowMap");
    }

    @Override
    protected void postLoading()
    {
        ShaderControls.setUniform2F(screenSize, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
        
        ShaderControls.setUniform1I(shadowMap, 1);
    } 
    
    public static HudShader shader()
    {
        return instance;
    }

    @Override
    protected String getVertexName()
    {
        return "HudVertex.shader";
    }

    @Override
    protected String getFragmentName()
    {
        return "HudFragment.shader";
    }
}
