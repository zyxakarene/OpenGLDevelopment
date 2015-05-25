package game.world.io;

import game.world.map.Tile;
import java.nio.ByteBuffer;

class TileIO
{

    public static int BYTE_PER_TILE = 3;

    static void save(ByteBuffer buffer, Tile tile)
    {
        buffer.put(tile.getTileType());
        buffer.put(getByteHeight(tile));
        buffer.put(getByteAngel(tile));
    }

    static Tile load(ByteBuffer buffer)
    {
        int tileType = buffer.get();
        int height = buffer.get();
        int angel = buffer.get();
        
        Tile tile = new Tile(tileType);
        tile.setZ(height * Tile.HEIGHT);
        tile.setYaw(angel * 90);
        
        return tile;
    }

    private static byte getByteAngel(Tile tile)
    {
        int tileAngle = (int) tile.getYaw();
        
        while (tileAngle > 360)
        {            
            tileAngle -= 360;
        }
        
        switch (tileAngle)
        {
            case (0):
                return 0;
            case (90):
                return 1;
            case (180):
                return 2;
            case (270):
                return 3;
            default:
                return 0;
        }
    }

    private static byte getByteHeight(Tile tile)
    {
        return (byte) (tile.getZ() / Tile.HEIGHT);
    }
}
