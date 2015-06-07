package game.ai.towers;

import game.world.map.Tile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import utils.interfaces.IDrawable;
import utils.interfaces.IPositionable;
import utils.interfaces.IShadowable;
import utils.interfaces.ITower;
import utils.interfaces.IUpdateable;

public class TowerManager implements IUpdateable, IDrawable, IShadowable
{

    public static final TowerManager instance = new TowerManager();
    private ArrayList<ITower> towers;
    private List<ITower> publicTowers;
    private HashMap<IPositionable, ITower> occupiedTiles;

    private TowerManager()
    {
        towers = new ArrayList<>();
        occupiedTiles = new HashMap<>();
        publicTowers = Collections.unmodifiableList(towers);
    }

    @Override
    public void update(int elapsedTime)
    {
        for (ITower tower : towers)
        {
            tower.update(elapsedTime);
        }
    }

    @Override
    public void draw()
    {
        for (ITower tower : towers)
        {
            tower.draw();
        }
        
        for (ITower tower : towers)
        {
            tower.drawProjectiles();
        }
    }

    @Override
    public void drawShadow()
    {
        for (ITower tower : towers)
        {
            tower.drawShadow();
        }
    }

    public List<ITower> getTowers()
    {
        return publicTowers;
    }

    public void addTowerAt(IPositionable tile)
    {
        if (occupiedTiles.containsKey(tile) == false)
        {
            Tower tower = new Tower();
            tower.setX(tile.getX());
            tower.setY(tile.getY());
            tower.setZ(tile.getZ());

            towers.add(tower);

            occupiedTiles.put(tile, tower);
        }
    }
}
