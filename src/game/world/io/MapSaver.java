package game.world.io;

import game.ai.MapSolver;
import game.world.map.Tile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import org.lwjgl.BufferUtils;
import utils.exceptions.Msg;

public class MapSaver
{

    /*
     * Map format:
     * int - startX
     * int - startY
     * int - MapWidth
     * int - MapHeight
     * Tile[mapWidth * MapHeight] - MapTiles
     * int - pathCount
     * Path[pathCount] - PathNodes
     */
    
    /*
     * Tile
     * {
     *    byte - Type
     *    byte - Height
     *    byte - Angle
     * }
     * 
     * Path
     * {
     *    float - X
     *    float - Y
     *    float - Z
     * }
     */
    
    private Tile[][] tiles;

    public MapSaver(Tile[][] tiles)
    {
        this.tiles = tiles;
        new MapSolver().solveForMap(tiles, 0, 2);
    }

    public void saveMapTo(String name)
    {
        try
        {
            FileChannel channel = getChannel(name);
            ByteBuffer buffer = getFilledBuffer();

            saveBufferToChannel(buffer, channel);
        }
        catch (FileNotFoundException ex)
        {
            Msg.error("Could not create the map file.", ex);
        }
        catch (IOException ex)
        {
            Msg.error("Could not save the map.", ex);
        }
    }

    private void saveBufferToChannel(ByteBuffer buffer, FileChannel channel) throws IOException
    {
        channel.write(buffer);
        channel.close();
    }

    private FileChannel getChannel(String name) throws FileNotFoundException
    {
        File file = new File(name);
        return new FileOutputStream(file, false).getChannel();
    }

    private ByteBuffer getFilledBuffer()
    {
        int finalByteCount = getFinalByteCount();

        ByteBuffer buffer = BufferUtils.createByteBuffer(finalByteCount);

        buffer.putInt(0);
        buffer.putInt(2);
        buffer.putInt(tiles.length);
        buffer.putInt(tiles[0].length);

        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles[i].length; j++)
            {
                TileIO.save(buffer, tiles[i][j]);
            }
        }
        
        buffer.putInt(MapSolver.finalPath.size());
        System.out.println(MapSolver.finalPath.size() * 3);
        for (int i = 0; i < MapSolver.finalPath.size(); i++)
        {
            PathIO.savePath(buffer, MapSolver.finalPath.get(i));
        }
        
        buffer.rewind();

        return buffer;
    }

    private int getFinalByteCount()
    {
        int tileCount = (tiles.length * tiles[0].length);
        int mapWidth = Integer.SIZE / 8;
        int mapHeight = Integer.SIZE / 8;
        
        int startX = Integer.SIZE / 8;
        int startY = Integer.SIZE / 8;
        int pathCount = Integer.SIZE / 8;
        
        int pathNodeCount = MapSolver.finalPath.size() * PathIO.BYTE_PER_NODE;

        int finalByteCount = (tileCount * TileIO.BYTE_PER_TILE) + pathNodeCount + startX + startY + pathCount + mapWidth + mapHeight;

        return finalByteCount;
    }
}
