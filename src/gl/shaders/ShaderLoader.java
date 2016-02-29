package gl.shaders;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class ShaderLoader
{

    private static final HashMap<ShaderType, AbstractShader> shaderMap = new HashMap<>();
    private static ShaderType currentShader;

    public static void loadShaders() throws FileNotFoundException, URISyntaxException
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
        
        UIShader uiShader = new UIShader();
        uiShader.load();
        
        LineShader lineShader = new LineShader();
        lineShader.load();
        
        PhysShader physShader = new PhysShader();
        physShader.load();

        shaderMap.put(ShaderType.TRANSFORM, transformShader);
        shaderMap.put(ShaderType.DEBTH, depthShader);
        shaderMap.put(ShaderType.HUD, hudShader);
        shaderMap.put(ShaderType.CLICK, clickShader);
        shaderMap.put(ShaderType.SKYBOX, skyboxShader);
        shaderMap.put(ShaderType.UI, uiShader);
        shaderMap.put(ShaderType.LINE, lineShader);
        shaderMap.put(ShaderType.PHYS, physShader);
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
