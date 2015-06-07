package game.ai.enemies;

import gl.shaders.TransformShader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utils.interfaces.IDrawable;
import utils.interfaces.IEnemy;
import utils.interfaces.IShadowable;
import utils.interfaces.IUpdateable;

public class EnemyManager implements IUpdateable, IDrawable, IShadowable
{

    public static final EnemyManager instance = new EnemyManager();
    private ArrayList<IEnemy> enemies;
    private List<IEnemy> publicEnemies;
    private IEnemy bufferEnemy;

    private EnemyManager()
    {
        enemies = new ArrayList<>();
        publicEnemies = Collections.unmodifiableList(enemies);
    }

    public List<IEnemy> getEnemies()
    {
        return publicEnemies;
    }

    @Override
    public void update(int elapsedTime)
    {
        for (int i = 0; i < enemies.size(); i++)
        {
            bufferEnemy = enemies.get(i);

            bufferEnemy.update(elapsedTime);

            if (bufferEnemy.isAlive() == false)
            {
                enemies.remove(i);
                i--;
            }
        }
    }

    @Override
    public void draw()
    {
        for (IEnemy enemy : enemies)
        {
            enemy.draw();
        }
        
        TransformShader.shader().setOverlayColor(1, 1, 1);
    }

    @Override
    public void drawShadow()
    {
        for (IEnemy enemy : enemies)
        {
            enemy.drawShadow();
        }
    }

    public void addEnemy()
    {
        enemies.add(new Enemy());
    }
}
