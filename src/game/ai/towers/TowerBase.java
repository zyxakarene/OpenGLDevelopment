package game.ai.towers;

import game.world.basic.GameEntity;
import utils.constants.TextureConstants;
import utils.constants.TowerTypes;
import utils.interfaces.IPositionable;


class TowerBase extends GameEntity
{

    TowerBase()
    {
        setModel(TowerTypes.BASE);
        setTexture(TextureConstants.TOWER_BASE);
    }
    
    void moveTo(IPositionable pos)
    {
        setPos(pos.getX(), pos.getY(), pos.getZ());
    }
    
}
