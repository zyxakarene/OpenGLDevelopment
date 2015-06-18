package game.ai.enemies;

import game.ai.MapSolver;
import game.sound.SoundManager;
import game.sound.Sounds;
import game.world.basic.MovingEntity;
import gl.shaders.TransformShader;
import java.util.ArrayList;
import utils.FloatMath;
import utils.constants.EnemyTypes;
import utils.constants.TextureConstants;
import utils.geometry.Point3D;
import utils.interfaces.IEnemy;
import utils.interfaces.IPositionable;

public class Enemy extends MovingEntity implements IEnemy
{

    private IPositionable projectileHit;
    private int health;
    private boolean recentlyShot;
    private int shotDuration;
    private int timeSinceShot;

    public Enemy()
    {
        setModel(EnemyTypes.TYPE_ONES_NAME);
        setTexture(TextureConstants.ENEMY_SET);

        health = 100;
        shotDuration = 250;

        projectileHit = new Point3D(getX(), getY(), getZ() + 1.5f);
        
        SoundManager.playSound(Sounds.ENEMY, this);
    }

    @Override
    public void draw()
    {
        if (recentlyShot)
        {
            float sine = FloatMath.sin(timeSinceShot / 50f);
            TransformShader.shader().setOverlayColor(1, sine, sine);
        }
        else
        {
            TransformShader.shader().setOverlayColor(1, 1, 1);
        }
        super.draw(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(int elapsedTime)
    {        
        if (recentlyShot)
        {
            timeSinceShot += elapsedTime;

            if (timeSinceShot >= shotDuration)
            {
                timeSinceShot = 0;
                recentlyShot = false;
            }
        }

        projectileHit.setX(getX());
        projectileHit.setY(getY());
        projectileHit.setZ(getZ() + 1.5f);

        super.update(elapsedTime); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ArrayList<IPositionable> getPath()
    {
        return MapSolver.finalPath;
    }

    @Override
    public void attack(int damage)
    {
        if (isAlive())
        {
            recentlyShot = true;
            health -= damage;
            
            if (isAlive() == false)
            {
                SoundManager.stopSoundFrom(this);
            }
        }
    }

    @Override
    public boolean isAlive()
    {
        return health > 0;
    }

    @Override
    protected void hitEndPath()
    {
    }

    @Override
    protected float getHorizontalSpeed()
    {
        return 0.01f;
    }

    @Override
    protected float getVerticalSpeed()
    {
        return 0.013f;
    }

    @Override
    protected float getHitBias()
    {
        return 0.1f;
    }

    @Override
    public IPositionable getProjectilePoint()
    {
        return projectileHit;
    }

    @Override
    protected void updateEntityRotations(float yaw, float roll, boolean toTheLeft)
    {
        setYaw(yaw + 180);

        //TODO: Fix
        if (lookVertically() && false)
        {
            if (roll != 0)
            {
                if (yaw == 180)
                {
                    setRoll(-roll);
                }
                else if (yaw == -180)
                {
                    setRoll(roll + 90);
                }
                else if (yaw == 90)
                {
                    setPitch(roll - 180);
                }
                else if (yaw == 270)
                {
                    setPitch(roll);
                }
            }
        }
    }

    protected boolean lookVertically()
    {
        return true;
    }
}
