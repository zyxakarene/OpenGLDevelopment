package game.world.map;

import game.world.basic.GameEntity;
import utils.constants.DoodadTypes;
import utils.constants.TextureConstants;
import utils.constants.TileTypes;
import utils.interfaces.IEntity;

public class Doodad extends GameEntity
{

    public Doodad(IEntity position, int tileType)
    {
        setModel(tileType == TileTypes.PLANE ? DoodadTypes.PLANE_GRASS : DoodadTypes.CLIFF_STRAIGHT_GRASS);
        setTexture(TextureConstants.TILES);

        setX(position.getX());
        setY(position.getY());
        setZ(position.getZ());

        setYaw(position.getYaw());
    }
}
