package gl.shaders;

import gl.glUtils.ShaderControls;
import org.lwjgl.util.vector.Matrix4f;

public class SkyboxShader extends AbstractShader
{

    private static SkyboxShader instance;
    private int projectUniform;
    private int viewUniform;
    private int texUniform;

    SkyboxShader()
    {
        instance = this;
    }

    @Override
    protected void setupUniforms()
    {
        texUniform = ShaderControls.createUniform(shaderProgram, "tex");
        projectUniform = ShaderControls.createUniform(shaderProgram, "proj");
        viewUniform = ShaderControls.createUniform(shaderProgram, "view");
    }

    @Override
    protected void postLoading()
    {
        ShaderControls.setUniform1I(texUniform, 0);
    }

    @Override
    protected String getVertexName()
    {
        return "SkyboxVertex.shader";
    }

    @Override
    protected String getFragmentName()
    {
        return "SkyboxFragment.shader";
    }
    
    public static SkyboxShader shader()
    {
        return instance;
    }
    
    public void updateViewUniform()
    {
        uniformBuffer.clear();
        SHARED_VIEW_TRANSFORM.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(viewUniform, uniformBuffer);
    }

    public void setupProjection(Matrix4f matrix)
    {
        uniformBuffer.clear();
        matrix.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(projectUniform, uniformBuffer);
    }
}
