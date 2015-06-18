package game.ai.towers;

import game.ai.towers.projectiles.Projectile;
import game.world.basic.GameEntity;
import java.util.ArrayList;
import utils.FloatMath;
import game.ai.towers.projectiles.ProjectileType;
import game.sound.SoundManager;
import game.sound.Sounds;
import utils.constants.TextureConstants;
import utils.constants.TowerTypes;
import utils.interfaces.IEnemy;
import utils.interfaces.IPositionable;
import utils.interfaces.ITower;

public class Tower extends GameEntity implements ITower
{

    private int reloadTime;
    private int timeSinceLastShot;
    private IEnemy currentTarget;
    private ArrayList<Projectile> projectiles;

    public Tower()
    {
        setModel(TowerTypes.BASE);
        setTexture(TextureConstants.TOWER_BASE);
        reloadTime = 1000;
        projectiles = new ArrayList<>();
        timeSinceLastShot = reloadTime;
    }

    @Override
    public int getRange()
    {
        //23 is aprox one tile non diagonally
        return 23 * 7 * 100;
    }

    @Override
    public void update(int elapsedTime)
    {
        timeSinceLastShot += elapsedTime;

        Projectile projectile;
        for(int i = 0; i < projectiles.size(); i++)
        {
            projectile = projectiles.get(i);
            projectile.update(elapsedTime);
            
            if (projectile.hasHit())
            {
                projectiles.remove(i);
                i--;
            }
        }
    }

    @Override
    public boolean canFire()
    {
        return timeSinceLastShot >= reloadTime;
    }

    @Override
    public void shootAt(IEnemy enemy)
    {
        lookAt(enemy);

        if (canFire())
        {
            SoundManager.playSound(Sounds.ROCKET_LAUNCH, this);
            Projectile projectile = new Projectile(this, enemy, getProjectile());
            projectiles.add(projectile);
            timeSinceLastShot = 0;
        }
    }

    private void lookAt(IPositionable enemy)
    {
        float width = getY() - enemy.getY();
        float lenght = getX() - enemy.getX();

        float newYaw;

        if (Math.abs(enemy.getX() - getX()) <= 0.001)
        {
            if (enemy.getY() > getY())
            {
                newYaw = 270;
            }
            else
            {
                newYaw = 90;
            }
        }
        else
        {
            float c = FloatMath.sqrt((lenght * lenght) + (width * width));
            float tempAngle = FloatMath.acos(((lenght * lenght) + (c * c) - (width * width)) / (2 * lenght * c));

            if (width < 0)
            {
                newYaw = FloatMath.toDegrees(tempAngle - (tempAngle * 2));
            }
            else
            {
                newYaw = FloatMath.toDegrees(tempAngle);
            }
        }

        setYaw(newYaw + 180);
    }

    @Override
    public IEnemy getCurrentTarget()
    {
        return currentTarget;
    }

    @Override
    public boolean hasTarget()
    {
        return currentTarget != null;
    }

    @Override
    public void drawProjectiles()
    {
        for (Projectile projectile : projectiles)
        {
            projectile.draw();
        }
    }

    @Override
    public ProjectileType getProjectile()
    {
        return ProjectileType.ROCKET;
    }
}
