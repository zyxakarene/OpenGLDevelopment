package gl.shaders;

import gl.glUtils.ShaderControls;
import org.lwjgl.util.vector.Matrix4f;

public class LineShader extends AbstractShader
{

    private static LineShader instance;
    private int clickColorUniform;
    private int viewUniform;
    private int projUniform;

    LineShader()
    {
        instance = this;
    }

    @Override
    protected void setupUniforms()
    {
        clickColorUniform = ShaderControls.createUniform(shaderProgram, "color");
        viewUniform = ShaderControls.createUniform(shaderProgram, "view");
        projUniform = ShaderControls.createUniform(shaderProgram, "proj");
    }

    @Override
    protected void postLoading()
    {
        ShaderControls.setUniform3F(clickColorUniform, 1, 0, 0);
    }

    public void setLineColor(float r, float g, float b)
    {
        ShaderLoader.activateShader(ShaderType.LINE);
        ShaderControls.setUniform3F(clickColorUniform, r, g, b);
    }

    public void setupProjection(Matrix4f matrix)
    {
        uniformBuffer.clear();
        matrix.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(projUniform, uniformBuffer);
    }

    public void updateViewUniform()
    {
        uniformBuffer.clear();
        SHARED_VIEW_TRANSFORM.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(viewUniform, uniformBuffer);
    }

    public static LineShader shader()
    {
        return instance;
    }

    @Override
    protected String getVertexName()
    {
        return "LineVertex.shader";
    }

    @Override
    protected String getFragmentName()
    {
        return "LineFragment.shader";
    }
    
    public int getPublicProgram()
    {
        return shaderProgram;
    }
}
