package game.physics;

import game.control.clicker.ClickRegistrator;
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
        ClickRegistrator.register(this);
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
    
    @Override
    public void onClick(int mouseKey)
    {
        PhysicsManager.instance.clicked(this);
    }
}
