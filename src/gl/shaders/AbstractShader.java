package gl.shaders;

import gl.glUtils.ShaderControls;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.FloatBuffer;
import java.util.Scanner;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

public abstract class AbstractShader
{

    protected static final Matrix4f SHARED_VIEW_TRANSFORM = SharedShaderObjects.SHARED_VIEW_TRANSFORM;
    protected static final Matrix4f SHARED_MODEL_TRANSFORM = SharedShaderObjects.SHARED_MODEL_TRANSFORM;
    protected final FloatBuffer uniformBuffer = BufferUtils.createFloatBuffer(16);
    protected int vertexShader, fragmentShader;
    protected int shaderProgram;

    AbstractShader()
    {
    }

    final void load() throws FileNotFoundException, URISyntaxException
    {
        vertexShader = ShaderControls.generateVertexShader();
        ShaderControls.createShaderFrom(vertexShader, loadFile(getVertexName()));

        fragmentShader = ShaderControls.generateFragmentShader();
        ShaderControls.createShaderFrom(fragmentShader, loadFile(getFragmentName()));

        shaderProgram = ShaderControls.createShaderProgram(vertexShader, fragmentShader);
        ShaderControls.bindFragmentLocation(shaderProgram, 0, "outColor");

        ShaderControls.link(shaderProgram);
        ShaderControls.use(shaderProgram);

        setupUniforms();
        postLoading();
    }

    protected void postLoading()
    {
        
    }

    protected void setupUniforms()
    {
        
    }

    protected abstract String getVertexName();

    protected abstract String getFragmentName();

    final void activate()
    {
        ShaderControls.use(shaderProgram);
    }
    
    public final int getProgram()
    {
        return shaderProgram;
    }

    private static String loadFile(String name) throws FileNotFoundException, URISyntaxException
    {
        StringBuilder builder = new StringBuilder();

        InputStream input = AbstractShader.class.getResourceAsStream("/gl/shaders/source/" + name);
        

        Scanner fileScan = new Scanner(input);
        while (fileScan.hasNextLine())
        {
             builder.append(fileScan.nextLine());
            builder.append("\n");
        }
        fileScan.close();

        return builder.toString();
    }
}
