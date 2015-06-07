package game.ai;

import game.ai.enemies.EnemyManager;
import game.ai.towers.TowerManager;
import java.util.List;
import utils.FloatMath;
import utils.interfaces.IEnemy;
import utils.interfaces.ITower;
import utils.interfaces.IUpdateable;

public class AttackManager implements IUpdateable
{

    private static List<ITower> towers = TowerManager.instance.getTowers();
    private static List<IEnemy> enemies = EnemyManager.instance.getEnemies();
    public static final AttackManager instance = new AttackManager();

    private AttackManager()
    {
    }

    @Override
    public void update(int elapsedTime)
    {
        for (ITower tower : towers)
        {
            findTargetFor(tower);
        }
    }

    private void findTargetFor(ITower tower)
    {
        IEnemy target = tower.getCurrentTarget();
        
        if(target == null || target.isAlive() == false)
        {
            target = findNearestEnemyTo(tower);
        }

        if (target != null)
        {
            tower.shootAt(target);
        }
    }

    private IEnemy findNearestEnemyTo(ITower tower)
    {
        int maxDistance = tower.getRange();
        
        if (tower.hasTarget() && FloatMath.getDistance(tower, tower.getCurrentTarget()) <= maxDistance)
        {
            return tower.getCurrentTarget();
        }
                
        for (IEnemy enemy : enemies)
        {
            float newDistance = FloatMath.getDistance(tower, enemy);
            if (newDistance < maxDistance)
            {
                return enemy;
            }
        }

        return null;
    }
}
