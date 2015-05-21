package gl.shaders;

import gl.glUtils.ShaderControls;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

public class SimpleDepthShader
{

    private static final FloatBuffer uniformBuffer = BufferUtils.createFloatBuffer(16);
    private static final Matrix4f SHARED_VIEW_TRANSFORM = SharedShaderObjects.SHARED_VIEW_TRANSFORM;
    private static final Matrix4f SHARED_MODEL_TRANSFORM = SharedShaderObjects.SHARED_MODEL_TRANSFORM;
    private static final String vertex = "SimpleDepthVertex.shader";
    private static final String fragment = "SimpleDepthFragment.shader";
    private static int vertexShader, fragmentShader;
    private static int shaderProgram;
    private static int projectionUniform;
    private static int viewUniform;
    private static int modelUniform;

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
        projectionUniform = ShaderControls.createUniform(shaderProgram, "proj");
        viewUniform = ShaderControls.createUniform(shaderProgram, "view");
        modelUniform = ShaderControls.createUniform(shaderProgram, "model");
    }

    public static void updateModelUniform()
    {
        uniformBuffer.clear();
        SHARED_MODEL_TRANSFORM.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(modelUniform, uniformBuffer);
    }

    public static void setupProjection(Matrix4f matrix)
    {
        uniformBuffer.clear();
        matrix.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(projectionUniform, uniformBuffer);
    }

    public static void updateViewUniform()
    {
        uniformBuffer.clear();
        SHARED_VIEW_TRANSFORM.store(uniformBuffer);
        uniformBuffer.flip();
        ShaderControls.setUniformMatrix(viewUniform, uniformBuffer);
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
