package gl.shaders;

import gl.glUtils.ShaderControls;
import org.lwjgl.util.vector.Matrix4f;

public class ClickShader extends AbstractShader
{

    private static ClickShader instance;
    private int clickColorUniform;
    private int modelUniform;
    private int viewUniform;
    private int projUniform;
    private static float r, g, b;

    ClickShader()
    {
        instance = this;
    }

    @Override
    protected void setupUniforms()
    {
        clickColorUniform = ShaderControls.createUniform(shaderProgram, "clickColor");
        modelUniform = ShaderControls.createUniform(shaderProgram, "model");
        viewUniform = ShaderControls.createUniform(shaderProgram, "view");
        projUniform = ShaderControls.createUniform(shaderProgram, "proj");
    }

    @Override
    protected void postLoading()
    {
        ShaderControls.setUniform3F(clickColorUniform, 1, 0, 0);
    }

    public void setClickColor(float r, float g, float b)
    {
        ShaderLoader.activateShader(ShaderType.CLICK);
        ShaderControls.setUniform3F(clickColorUniform, r, g, b);
    }

    public void updateModelUniform()
    {
        uniformBuffer.clear();
        SHARED_MODEL_TRANSFORM.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(modelUniform, uniformBuffer);
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

    public static ClickShader shader()
    {
        return instance;
    }

    @Override
    protected String getVertexName()
    {
        return "ClickVertex.shader";
    }

    @Override
    protected String getFragmentName()
    {
        return "ClickFragment.shader";
    }
}
