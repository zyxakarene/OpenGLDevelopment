package game.world.io;

import game.world.map.Tile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import utils.exceptions.Msg;

public class MapLoader
{

    public Tile[][] loadMap(String name)
    {
        try
        {
            RandomAccessFile raf = createFile(name);
            ByteBuffer buffer = createBuffer(raf);

            return createMapFromBuffer(buffer);
        }
        catch (FileNotFoundException ex)
        {
            Msg.error("Could not open the map file.", ex);
        }
        catch (IOException ex)
        {
            Msg.error("Could not read the map.", ex);
        }
        
        return new Tile[0][0];
    }

    private Tile[][] createMapFromBuffer(ByteBuffer buffer)
    {
        int width = buffer.getInt();
        int height = buffer.getInt();

        Tile[][] tiles = new Tile[width][height];

        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles[i].length; j++)
            {
                tiles[i][j] = TileIO.load(buffer);
                tiles[i][j].setX(Tile.WIDTH * i);
                tiles[i][j].setY(Tile.WIDTH * j);
            }
        }

        return tiles;
    }

    private RandomAccessFile createFile(String name) throws FileNotFoundException
    {
        File file = new File(name);
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        return raf;
    }

    private ByteBuffer createBuffer(RandomAccessFile raf) throws IOException
    {
        int fileLenght = (int) raf.length();
        byte[] buffer = new byte[fileLenght];
        raf.read(buffer);

        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        return byteBuffer;
    }
}
