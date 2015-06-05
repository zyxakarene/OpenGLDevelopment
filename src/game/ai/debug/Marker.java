package game.ai.debug;

import game.world.basic.GameEntity;
import utils.constants.TextureConstants;

public class Marker extends GameEntity
{

    public Marker()
    {
        setModel("cube");
        setTexture(TextureConstants.SKYBOX);
        setScale(0.05f);
    }
}
