package game.world.map;

import game.ai.TilePaths;
import game.ai.towers.TowerManager;
import game.control.clicker.ClickRegistrator;
import game.world.basic.GameEntity;
import org.lwjgl.util.vector.Vector3f;
import utils.constants.TextureConstants;
import utils.constants.TileTypes;

public class Tile extends GameEntity
{

    public static final int WIDTH = 5;
    public static final int HEIGHT = 4;
    public static String changeTo = TileTypes.PLANE_NAME;
    private int tileType;

    public Vector3f[] getPath()
    {
        return path;
    }
    private Vector3f[] path;

    public Tile(int tileType)
    {
        this.tileType = tileType;

        setModel(TileTypes.idToName(tileType));
        setTexture(TextureConstants.TILES);

        if (TileTypes.isLane(tileType) == false)
        {
            ClickRegistrator.register(this);
        }
    }

    @Override
    public void onClick(int mouseKey)
    {
        switch (mouseKey)
        {
            case (1):
            {
                TowerManager.instance.addTowerAt(this);
                break;
            }
        }
    }

    public byte getTileType()
    {
        return (byte) tileType;
    }

    public void readyForPath()
    {
        if (TileTypes.isLane(tileType))
        {
            path = TilePaths.getPaths(tileType);
            TilePaths.transform(path, getX(), getY(), getZ(), getPitch(), getYaw(), getRoll());
        }
    }
}
