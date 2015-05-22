package gl.shaders;

import gl.glUtils.ShaderControls;
import org.lwjgl.util.vector.Matrix4f;

public class SimpleDepthShader extends AbstractShader
{

    private static SimpleDepthShader instance;
    private static int projectionUniform;
    private static int viewUniform;
    private static int modelUniform;

    SimpleDepthShader()
    {
        instance = this;
    }

    @Override
    protected void setupUniforms()
    {
        projectionUniform = ShaderControls.createUniform(shaderProgram, "proj");
        viewUniform = ShaderControls.createUniform(shaderProgram, "view");
        modelUniform = ShaderControls.createUniform(shaderProgram, "model");
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
        ShaderControls.setUniformMatrix(projectionUniform, uniformBuffer);
    }

    public void updateViewUniform()
    {
        uniformBuffer.clear();
        SHARED_VIEW_TRANSFORM.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(viewUniform, uniformBuffer);
    }

    public static SimpleDepthShader shader()
    {
        return instance;
    }

    @Override
    protected String getVertexName()
    {
        return "SimpleDepthVertex.shader";
    }

    @Override
    protected String getFragmentName()
    {
        return "SimpleDepthFragment.shader";
    }
}
