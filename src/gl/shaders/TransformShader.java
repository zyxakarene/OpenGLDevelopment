package gl.shaders;

import gl.glUtils.ShaderControls;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

public class TransformShader
{

    private static final FloatBuffer uniformBuffer = BufferUtils.createFloatBuffer(16);
    private static final Matrix4f SHARED_VIEW_TRANSFORM = SharedShaderObjects.SHARED_VIEW_TRANSFORM;
    private static final Matrix4f SHARED_MODEL_TRANSFORM = SharedShaderObjects.SHARED_MODEL_TRANSFORM;
    
    private static final String vertex = "TransformVertex.shader";
    private static final String fragment = "TransformFragment.shader";
    private static int vertexShader, fragmentShader;
    private static int shaderProgram;
    private static int viewPosUniform;
    private static int lightDirectionUniform;
    private static int lightColUniform;
    private static int viewUniform;
    private static int projectUniform;
    private static int modelUniform;
    private static int texUniform;
    
    private static int lightViewUniform;
    private static int lightProjUniform;
    private static int shadowMap;

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

        setupUniforms();
    }

    private static void setupUniforms()
    {
        texUniform = ShaderControls.createUniform(shaderProgram, "tex");
        modelUniform = ShaderControls.createUniform(shaderProgram, "model");
        viewUniform = ShaderControls.createUniform(shaderProgram, "view");
        projectUniform = ShaderControls.createUniform(shaderProgram, "proj");
        lightColUniform = ShaderControls.createUniform(shaderProgram, "lightColor");
        lightDirectionUniform = ShaderControls.createUniform(shaderProgram, "lightDirection");
        viewPosUniform = ShaderControls.createUniform(shaderProgram, "viewPos");
        
        lightViewUniform = ShaderControls.createUniform(shaderProgram, "lightView");
        lightProjUniform = ShaderControls.createUniform(shaderProgram, "lightProj");
        shadowMap = ShaderControls.createUniform(shaderProgram, "shadowMap");
           
        
        ShaderControls.setUniform3F(lightColUniform, 1f, 1f, 1f);
        ShaderControls.setUniform3F(viewPosUniform, 0, 0, 0);
        ShaderControls.setUniform3F(lightDirectionUniform, -0.2f, -0.45f, -0.3f);
//        ShaderControls.setUniform1I(texUniform, 0);
//        ShaderControls.setUniform1I(shadowMap, 1);
    }
    
    public static void setViewPos(float x, float y, float z)
    {
        ShaderControls.setUniform3F(viewPosUniform, x, y, z);
    }
    
    public static void setLightDirection(float pitch, float roll, float yaw)
    {
        ShaderControls.setUniform3F(lightDirectionUniform, pitch, roll, yaw);
    }

    public static int getProgram()
    {
        return shaderProgram;
    }

    public static void updateModelUniform()
    {
        uniformBuffer.clear();
        SHARED_MODEL_TRANSFORM.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(modelUniform, uniformBuffer);
    }

    public static void activate()
    {
        ShaderControls.use(shaderProgram);
    }

    public static void setupProjection(Matrix4f matrix)
    {
        uniformBuffer.clear();
        matrix.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(projectUniform, uniformBuffer);
    }
    
    public static void updateViewUniform()
    {
        uniformBuffer.clear();
        SHARED_VIEW_TRANSFORM.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(viewUniform, uniformBuffer);
    }

    public static void updateLightViewUniform()
    {
        uniformBuffer.clear();
        SHARED_VIEW_TRANSFORM.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(lightViewUniform, uniformBuffer);
    }

    public static void setupLightProjection(Matrix4f matrix)
    {
        uniformBuffer.clear();
        matrix.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(lightProjUniform, uniformBuffer);
    }
}
