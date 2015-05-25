package gl.shaders;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class ShaderLoader
{

    private static final HashMap<ShaderType, AbstractShader> shaderMap = new HashMap<>();
    private static ShaderType currentShader;

    public static void loadShaders() throws FileNotFoundException
    {
        AbstractShader transformShader = new TransformShader();
        transformShader.load();
        
        AbstractShader depthShader = new SimpleDepthShader();
        depthShader.load();
        
        AbstractShader hudShader = new HudShader();
        hudShader.load();
        
        AbstractShader clickShader = new ClickShader();
        clickShader.load();
        
        SkyboxShader skyboxShader = new SkyboxShader();
        skyboxShader.load();

        shaderMap.put(ShaderType.TRANSFORM, transformShader);
        shaderMap.put(ShaderType.DEBTH, depthShader);
        shaderMap.put(ShaderType.HUD, hudShader);
        shaderMap.put(ShaderType.CLICK, clickShader);
        shaderMap.put(ShaderType.SKYBOX, skyboxShader);
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
