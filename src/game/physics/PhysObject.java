package game.physics;

import game.world.basic.PhysEntity;
import utils.constants.TextureConstants;
import utils.constants.TowerTypes;
import utils.interfaces.IUpdateable;


public class PhysObject extends PhysEntity implements IUpdateable
{

    public PhysObject()
    {
        setModel(TowerTypes.BASE);
        setTexture(TextureConstants.TOWER_BASE);
    }

    @Override
    public void update(int elapsedTime)
    {
        PhysicsManager.instance.updateMe(this);
    }

    void updateFrom(float[] glMatrix)
    {
        model.physMatrix = glMatrix;
    }
}
