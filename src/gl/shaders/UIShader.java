package gl.shaders;

import gl.glUtils.ShaderControls;
import utils.constants.GameConstants;

public class UIShader extends AbstractShader
{

    private static UIShader instance;
    private int screenSize;
    private int texture;

    UIShader()
    {
        instance = this;
    }
    
    @Override
    protected void setupUniforms()
    {
        screenSize = ShaderControls.createUniform(shaderProgram, "screenSize");
        texture = ShaderControls.createUniform(shaderProgram, "tex");
    }

    @Override
    protected void postLoading()
    {
        ShaderControls.setUniform2F(screenSize, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
        
        ShaderControls.setUniform1I(texture, 0);
    } 
    
    public static UIShader shader()
    {
        return instance;
    }

    @Override
    protected String getVertexName()
    {
        return "UIVertex.shader";
    }

    @Override
    protected String getFragmentName()
    {
        return "UIFragment.shader";
    }
}
