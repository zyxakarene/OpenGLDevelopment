package gl.shaders;

import gl.glUtils.ShaderControls;

public class HudShader extends AbstractShader
{

    private static HudShader instance;
    private int shadowMap;

    HudShader()
    {
        instance = this;
    }
    
    @Override
    protected void setupUniforms()
    {
        shadowMap = ShaderControls.createUniform(shaderProgram, "shadowMap");
    }

    @Override
    protected void postLoading()
    {
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
