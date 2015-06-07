package utils.interfaces;

import game.ai.towers.projectiles.ProjectileType;

public interface ITower extends IDrawable, IShadowable, IUpdateable, IPositionable
{

    public void drawProjectiles();

    public void shootAt(IEnemy enemy);

    public int getRange();

    public boolean canFire();

    public IEnemy getCurrentTarget();

    public boolean hasTarget();
    
    public ProjectileType getProjectile();
}
