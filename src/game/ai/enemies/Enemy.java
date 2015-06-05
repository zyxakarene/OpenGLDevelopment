package game.ai.enemies;

import game.ai.MapSolver;
import game.world.basic.MovingEntity;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import utils.constants.EnemyTypes;
import utils.constants.TextureConstants;
import utils.interfaces.IEnemy;

public class Enemy extends MovingEntity implements IEnemy
{

    private int health;

    public Enemy()
    {
        setModel(EnemyTypes.TYPE_ONES_NAME);
        setTexture(TextureConstants.ENEMY_SET);

        health = 100;
    }

    @Override
    protected ArrayList<Vector3f> getPath()
    {
        return MapSolver.finalPath;
    }

    @Override
    public void update(int elapsedTime)
    {
        if (isAlive())
        {
            super.update(elapsedTime);
        }
    }

    @Override
    public void attack(int damage)
    {
        if (isAlive())
        {
            health -= damage;
        }
    }

    @Override
    public boolean isAlive()
    {
        return health > 0;
    }
}
