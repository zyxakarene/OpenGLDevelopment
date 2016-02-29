package gl.shaders;

import gl.glUtils.ShaderControls;
import org.lwjgl.util.vector.Matrix4f;

public class PhysShader extends AbstractShader
{

    private static PhysShader instance;
    private int viewPosUniform;
    private int lightDirectionUniform;
    private int lightColUniform;
    private int viewUniform;
    private int projectUniform;
    private int modelUniform;
    private int lightViewUniform;
    private int lightProjUniform;
    
    private int overlayUniform;
    
    private int normalMap;
    private int shadowMap;
    private int texUniform;

    PhysShader()
    {
        instance = this;
    }

    @Override
    protected void setupUniforms()
    {
        normalMap = ShaderControls.createUniform(shaderProgram, "normalMap");
        texUniform = ShaderControls.createUniform(shaderProgram, "tex");
        modelUniform = ShaderControls.createUniform(shaderProgram, "model");
        viewUniform = ShaderControls.createUniform(shaderProgram, "view");
        projectUniform = ShaderControls.createUniform(shaderProgram, "proj");
        lightColUniform = ShaderControls.createUniform(shaderProgram, "lightColor");
        lightDirectionUniform = ShaderControls.createUniform(shaderProgram, "lightDirection");
        viewPosUniform = ShaderControls.createUniform(shaderProgram, "viewPos");
        overlayUniform = ShaderControls.createUniform(shaderProgram, "overlayColor");

        lightViewUniform = ShaderControls.createUniform(shaderProgram, "lightView");
        lightProjUniform = ShaderControls.createUniform(shaderProgram, "lightProj");
        shadowMap = ShaderControls.createUniform(shaderProgram, "shadowMap");
    }

    @Override
    protected void postLoading()
    {
        ShaderControls.setUniform3F(lightColUniform, 1f, 1f, 1f);
        ShaderControls.setUniform3F(viewPosUniform, 0, 0, 0);
        ShaderControls.setUniform3F(lightDirectionUniform, -0.2f, -0.45f, -0.3f);
        setOverlayColor(1, 1, 1);
        
        ShaderControls.setUniform1I(texUniform, 0);
        ShaderControls.setUniform1I(shadowMap, 1);
        ShaderControls.setUniform1I(normalMap, 2);
    }

    @Override
    protected String getVertexName()
    {
        return "PhysVertex.shader";
    }

    @Override
    protected String getFragmentName()
    {
        return "TransformFragment.shader";
    }
    
    public static PhysShader shader()
    {
        return instance;
    }

    public void setOverlayColor(float r, float g, float b)
    {
        ShaderControls.setUniform3F(overlayUniform, r, g, b);
    }
    
    public void setViewPos(float x, float y, float z)
    {
        ShaderControls.setUniform3F(viewPosUniform, x, y, z);
    }

    public void setLightDirection(float pitch, float roll, float yaw)
    {
        ShaderControls.setUniform3F(lightDirectionUniform, pitch, roll, yaw);
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
        ShaderControls.setUniformMatrix(projectUniform, uniformBuffer);
    }

    public void updateViewUniform()
    {
        uniformBuffer.clear();
        SHARED_VIEW_TRANSFORM.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(viewUniform, uniformBuffer);
    }

    public void updateLightViewUniform()
    {
        uniformBuffer.clear();
        SHARED_VIEW_TRANSFORM.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(lightViewUniform, uniformBuffer);
    }

    public void setupLightProjection(Matrix4f matrix)
    {
        uniformBuffer.clear();
        matrix.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(lightProjUniform, uniformBuffer);
    }
}