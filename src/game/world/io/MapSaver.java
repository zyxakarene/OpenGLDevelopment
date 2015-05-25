package game.world.io;

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

    private Tile[][] tiles;

    public MapSaver(Tile[][] tiles)
    {
        this.tiles = tiles;
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

        buffer.putInt(tiles.length);
        buffer.putInt(tiles[0].length);

        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles[i].length; j++)
            {
                TileIO.save(buffer, tiles[i][j]);
            }
        }
        
        buffer.rewind();

        return buffer;
    }

    private int getFinalByteCount()
    {
        int tileCount = (tiles.length * tiles[0].length);
        int mapWidth = Integer.SIZE / 8;
        int mapHeight = Integer.SIZE / 8;

        int finalByteCount = (tileCount * TileIO.BYTE_PER_TILE) + mapWidth + mapHeight;

        return finalByteCount;
    }
}
