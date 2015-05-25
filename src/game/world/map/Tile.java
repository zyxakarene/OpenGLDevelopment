package game.world.map;

import game.control.clicker.ClickRegistrator;
import game.world.basic.GameEntity;
import utils.constants.TextureConstants;
import utils.constants.TileTypes;

public class Tile extends GameEntity
{

    public static final int WIDTH = 5;
    public static final int HEIGHT = 4;
    public static String changeTo = TileTypes.PLANE_NAME;
    private int tileType;

    public Tile(int tileType)
    {
        this.tileType = tileType;

        setModel(TileTypes.idToName(tileType));
        setTexture(TextureConstants.TILES);

        ClickRegistrator.register(this);
    }

    @Override
    public void onClick(int mouseKey)
    {
        switch (mouseKey)
        {
            case (1):
            {
                setModel(changeTo);
                tileType = TileTypes.nameToId(changeTo);
                break;
            }
            case (2):
            {
                changeYaw(90);
                break;
            }
            case (4):
            {
                changeZ(HEIGHT);
                break;
            }
            case (5):
            {
                changeZ(-HEIGHT);
                break;
            }
        }
    }
    
    public byte getTileType()
    {
        return (byte) tileType;
    }

}
