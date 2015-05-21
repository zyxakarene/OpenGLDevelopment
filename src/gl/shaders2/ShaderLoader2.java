package gl.shaders2;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class ShaderLoader2
{

    private static final HashMap<ShaderType, AbstractShader> shaderMap = new HashMap<>();
    private static ShaderType currentShader;

    public static void loadShaders() throws FileNotFoundException
    {
        AbstractShader transformShader = new TransformShader();
        transformShader.load();
        ShaderType.TRANSFORM.shader = transformShader;

        shaderMap.put(ShaderType.TRANSFORM, transformShader);
    }

    public static void activateShader(ShaderType shader)
    {
        if (currentShader != shader)
        {
            currentShader = shader;
            shaderMap.get(shader).activate();
        }
    }
}
