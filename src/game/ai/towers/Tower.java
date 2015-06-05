package game.ai.towers;

import game.world.basic.GameEntity;
import utils.FloatMath;
import utils.constants.TextureConstants;
import utils.constants.TowerTypes;
import utils.interfaces.IEnemy;
import utils.interfaces.IPositionable;
import utils.interfaces.ITower;

public class Tower extends GameEntity implements ITower
{
    
    private int reloadTime;
    private int timeSinceLastShot;

    public Tower()
    {
        setModel(TowerTypes.BASE);
        setTexture(TextureConstants.TOWER_BASE);
        reloadTime = 1000;
    }

    protected int getDamage()
    {
        return 20;
    }

    @Override
    public int getRange()
    {
        //23 is aprox one time non diagonally
        return 23 * 3;
    }

    @Override
    public void update(int elapsedTime)
    {
        timeSinceLastShot += elapsedTime;
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
            System.out.println(this + " shot at " + enemy);
            enemy.attack(getDamage());
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
}
