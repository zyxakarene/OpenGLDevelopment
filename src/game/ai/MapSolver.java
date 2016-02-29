package game.ai;

import game.world.map.Tile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import utils.constants.TileTypes;
import utils.interfaces.IPositionable;

public class MapSolver
{

    private final ArrayList<Tile> allTiles;
    public static final ArrayList<IPositionable> finalPath = new ArrayList<>();

    public MapSolver()
    {
        allTiles = new ArrayList<>();
    }

    public static void addPathPoint(IPositionable point)
    {
        finalPath.add(point);
    }
    
    public void solveForMap(Tile[][] tiles, int xStart, int yStart)
    {
        finalPath.clear();
        
        Tile start = tiles[xStart][yStart];
        createAllTiles(tiles);

        addTilePath(start, true);
        allTiles.remove(start);

        while (allTiles.isEmpty() == false)
        {
            addNextTile();
        }
        
        checkDuplicates();
    }

    private IPositionable getLatestPoint()
    {
        return finalPath.get(finalPath.size() - 1);
    }

    private void createAllTiles(Tile[][] tiles)
    {
        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles[i].length; j++)
            {
                Tile tile = tiles[i][j];
                if (TileTypes.isLane(tile.getTileType()))
                {
                    allTiles.add(tiles[i][j]);
                }
            }
        }
    }

    private void addTilePath(Tile tile, boolean reverse)
    {
        IPositionable[] tilePath = tile.getPath();

        List<IPositionable> list = Arrays.asList(tilePath);
        
        System.out.println("FoundPath: " + list);
        
        if (reverse)
        {
            Collections.reverse(list);
        }

        finalPath.addAll(list);
    }

    private void addNextTile()
    {
        float closestMatch = Float.MAX_VALUE;
        boolean reverse = false;
        Tile tileToUse = null;

        for (int i = 0; i < allTiles.size(); i++)
        {
            Tile tile = allTiles.get(i);

            IPositionable current = getLatestPoint();

            IPositionable[] path = tile.getPath();
            IPositionable endOne = path[0];
            IPositionable endTwo = path[path.length - 1];

            float distanceOne = distance(current, endOne);
            float distanceTwo = distance(current, endTwo);

            if (distanceOne < distanceTwo)
            {
                if (distanceOne < closestMatch)
                {
                    tileToUse = tile;
                    reverse = false;
                    closestMatch = distanceOne;
                }
            }
            else
            {
                if (distanceTwo < closestMatch)
                {
                    tileToUse = tile;
                    reverse = true;
                    closestMatch = distanceTwo;
                }
            }
        }
        
        allTiles.remove(tileToUse);
        addTilePath(tileToUse, reverse);
    }

    private float distance(IPositionable p1, IPositionable p2)
    {
        Vector3f distance = new Vector3f(p2.getX() - p1.getX(), p2.getY() - p1.getY(), p2.getZ() - p1.getZ());

        return distance.lengthSquared();
    }

    private void checkDuplicates()
    {
        for (int i = 0; i < finalPath.size() - 1; i++)
        {
            IPositionable p1 = finalPath.get(i);
            IPositionable p2 = finalPath.get(i + 1);
            
            float dX = Math.abs(p1.getX() - p2.getX());
            float dY = Math.abs(p1.getY() - p2.getY());
            float dZ = Math.abs(p1.getZ() - p2.getZ());
            
            float distance = (dX *dX) + (dY * dY) + (dZ * dZ);

            if (distance <= 0)
            {
                finalPath.remove(i);
                i--;
            }
        }
    }
}
