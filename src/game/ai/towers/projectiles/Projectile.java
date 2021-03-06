package game.ai.towers.projectiles;

import game.camera.Camera;
import game.sound.SoundManager;
import game.world.basic.MovingEntity;
import java.util.ArrayList;
import utils.FloatMath;
import utils.interfaces.IEnemy;
import utils.interfaces.IPositionable;
import utils.interfaces.ITower;

public class Projectile extends MovingEntity
{

    private boolean hasHit;
    private ProjectileType type;
    private final IEnemy target;
    private final ArrayList<IPositionable> path;

    public Projectile(ITower shooter, IEnemy target, ProjectileType type)
    {
        this.target = target;
        this.type = type;

        path = new ArrayList<>();
        path.add(target.getProjectilePoint());

        setModel(type.model);
        setTexture(type.texture);
        setScale(0.4f);

        setPos(shooter.getX(), shooter.getY(), shooter.getZ() + 1.5f);

        SoundManager.playSound(type.getFireSound(), this);
        SoundManager.playSound(type.getFlySound(), this);
    }

    @Override
    public void update(int elapsedTime)
    {
        super.update(elapsedTime);
        recalculateAngle();
    }

    @Override
    protected ArrayList<IPositionable> getPath()
    {
        return path;
    }

    @Override
    protected void hitEndPath()
    {
        target.attack(type.damage);
        hasHit = true;
        SoundManager.stopSoundFrom(this);
        SoundManager.playSound(type.getHitSound(), target);

        float distanceToCamera = FloatMath.getDistance(this, Camera.position());
        if (distanceToCamera <= 500)
        {
            Camera.shakeFor(250);
        }
    }

    @Override
    protected float getHorizontalSpeed()
    {
        return 0.01f * 3;
    }

    @Override
    protected float getVerticalSpeed()
    {
        return 0.013f * 3;
    }

    @Override
    protected float getHitBias()
    {
        return 0.5f;
    }

    public boolean hasHit()
    {
        return hasHit;
    }

    @Override
    protected void updateEntityRotations(float rightLeft, float upDown, boolean toTheLeft)
    {
        setRoll(-upDown);

        if (toTheLeft)
        {
            setYaw(rightLeft);
        }
        else
        {
            setYaw(rightLeft + 180);
        }
    }
}
