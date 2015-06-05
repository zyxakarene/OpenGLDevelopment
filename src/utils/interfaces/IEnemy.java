package utils.interfaces;

public interface IEnemy extends IUpdateable, IDrawable, IShadowable, IPositionable
{

    public void attack(int damage);

    public boolean isAlive();
}
