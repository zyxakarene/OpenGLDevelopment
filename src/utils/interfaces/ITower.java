package utils.interfaces;

public interface ITower extends IDrawable, IShadowable, IUpdateable, IPositionable
{

    public void shootAt(IEnemy enemy);

    public int getRange();

    public boolean canFire();
}
