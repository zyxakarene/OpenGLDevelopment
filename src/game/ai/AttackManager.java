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
        IEnemy target = findNearestEnemyTo(tower);

        if (target != null)
        {
            tower.shootAt(target);
        }
    }

    private IEnemy findNearestEnemyTo(ITower tower)
    {
        int distance = tower.getRange();
        for (IEnemy enemy : enemies)
        {
            float newDistance = FloatMath.getDistance(tower, enemy);
            if (newDistance < distance)
            {
                return enemy;
            }
        }

        return null;
    }
}
