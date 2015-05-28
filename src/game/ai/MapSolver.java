package game.ai;

import game.world.map.Tile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import utils.constants.TileTypes;

public class MapSolver
{

    private final ArrayList<Tile> allTiles;
    public static final ArrayList<Vector3f> finalPath = new ArrayList<>();

    public MapSolver()
    {
        allTiles = new ArrayList<>();
    }

    public void solveForMap(Tile[][] tiles, int xStart, int yStart)
    {
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

    private Vector3f getLatestPoint()
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
        Vector3f[] tilePath = tile.getPath();

        List<Vector3f> list = Arrays.asList(tilePath);
        
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

            Vector3f current = getLatestPoint();

            Vector3f[] path = tile.getPath();
            Vector3f endOne = path[0];
            Vector3f endTwo = path[path.length - 1];

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

    private float distance(Vector3f p1, Vector3f p2)
    {
        Vector3f distance = new Vector3f(p2.x - p1.x, p2.y - p1.y, p2.z - p1.z);

        return distance.lengthSquared();
    }

    private void checkDuplicates()
    {
        for (int i = 0; i < finalPath.size() - 1; i++)
        {
            Vector3f p1 = finalPath.get(i);
            Vector3f p2 = finalPath.get(i + 1);
            
            float dX = Math.abs(p1.x - p2.x);
            float dY = Math.abs(p1.y - p2.y);
            float dZ = Math.abs(p1.z - p2.z);
            
            float distance = (dX *dX) + (dY * dY) + (dZ * dZ);

            if (distance <= 0)
            {
                finalPath.remove(i);
                i--;
            }
        }
    }
}
