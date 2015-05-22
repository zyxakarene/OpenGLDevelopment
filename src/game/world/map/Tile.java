package game.world.map;

import game.control.clicker.ClickRegistrator;
import game.world.basic.GameEntity;
import utils.constants.TextureConstants;
import utils.constants.TileTypes;

public class Tile extends GameEntity
{

    public static final int WIDTH = 5;
    public static final int HEIGHT = 4;

    public Tile(int tileType)
    {
        setModel(TileTypes.idToName(tileType));
        setTexture(TextureConstants.TILES);
        
        ClickRegistrator.register(this);
    }
}
