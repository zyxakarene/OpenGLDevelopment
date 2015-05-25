package game.world.map;

import game.camera.Camera;
import game.world.basic.Positioning;
import gl.models.ModelManager;
import gl.models.transforms.TransformModel;
import gl.shaders.ShaderLoader;
import gl.shaders.ShaderType;
import gl.textures.TextureManager;
import utils.constants.GameConstants;
import utils.constants.TextureConstants;
import utils.interfaces.IDrawable;


public class Skybox implements IDrawable
{

    private TransformModel model;
    
    public Skybox()
    {
        model = ModelManager.getModel(GameConstants.SKYBOX_MODEL);
        Positioning p = new Positioning();
        model.setPositionInfo(p);
        
    }

    @Override
    public void draw()
    {
        TextureManager.bind(TextureConstants.SKYBOX);
        ShaderLoader.activateShader(ShaderType.SKYBOX);
        model.draw();
        
        Camera.clearDepth();
    }

    
  
    
}
