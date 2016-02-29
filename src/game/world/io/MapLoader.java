package game.world.io;

import game.ai.MapSolver;
import game.world.map.Tile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import utils.constants.TileTypes;
import utils.exceptions.Msg;
import utils.interfaces.IPositionable;

public class MapLoader
{
    private int laneCount;
    private int planeCount;
    
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
        int startX = buffer.getInt();
        int startY = buffer.getInt();
        
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
                
                tiles[i][j].readyForPath();
                
                if (TileTypes.isLane(tiles[i][j].getTileType()))
                {
                    laneCount++;
                }
                else
                {
                    planeCount++;
                }
            }
        }
        
        int pathCount = buffer.getInt();
        System.out.println(pathCount + " nodes to load");
        for (int i = 0; i < pathCount; i++)
        {
            IPositionable position = PathIO.load(buffer);
            MapSolver.addPathPoint(position);
        }

        return tiles;
    }

    public int getLaneCount()
    {
        return laneCount;
    }

    public int getPlaneCount()
    {
        return planeCount;
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
