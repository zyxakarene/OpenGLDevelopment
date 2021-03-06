package game.world.basic;

import gl.models.ModelManager;
import gl.models.transforms.TransformModel;
import gl.textures.TextureManager;
import utils.constants.TextureConstants;
import utils.interfaces.IClickable;
import utils.interfaces.IDrawable;
import utils.interfaces.IEntity;
import utils.interfaces.IShadowable;

public abstract class GameEntity implements IDrawable, IShadowable, IClickable, IEntity
{
    private Positioning info;
    protected TransformModel model;
    private String texture;
    private String normal;

    public GameEntity()
    {
        normal = TextureConstants.TILES_NORMAL;
        info = Positioning.getDefault();
    }

    protected void setModel(String path)
    {
        model = ModelManager.getModel(path);
    }

    public void setPositionInfo(Positioning info)
    {
        this.info = info;
    }

    protected void setTexture(String texture)
    {
        this.texture = texture;
    }

    @Override
    public void draw()
    {
        TextureManager.bindNormal(normal);
        TextureManager.bind(texture);
        model.setPositionInfo(info);
        model.draw();
    }

    //<editor-fold defaultstate="collapsed" desc="Get and Set for positioning">
    public float getScale()
    {
        return info.scale;
    }

    public void setScale(float scale)
    {
        info.scale = scale;
    }

    public float getX()
    {
        return info.x;
    }

    public void setX(float x)
    {
        info.x = x;
    }

    public float getY()
    {
        return info.y;
    }

    public void setY(float y)
    {
        info.y = y;
    }

    public float getZ()
    {
        return info.z;
    }

    public void setZ(float z)
    {
        info.z = z;
    }
    
    public void setPos(float x, float y, float z)
    {
        info.x = x;
        info.y = y;
        info.z = z;
    }

    public float getPitch()
    {
        return info.pitch;
    }

    public void setPitch(float pitch)
    {
        info.pitch = pitch;
    }

    public float getRoll()
    {
        return info.roll;
    }

    public void setRoll(float roll)
    {
        info.roll = roll;
    }

    public float getYaw()
    {
        return info.yaw;
    }

    public void setYaw(float yaw)
    {
        info.yaw = yaw;
    }

    public void changeScale(float scale)
    {
        info.scale += scale;
    }

    public void changeX(float x)
    {
        info.x += x;
    }

    public void changeY(float y)
    {
        info.y += y;
    }

    public void changeZ(float z)
    {
        info.z += z;
    }

    public void changePitch(float pitch)
    {
        info.pitch += pitch;
    }

    public void changeRoll(float roll)
    {
        info.roll += roll;
    }

    public void changeYaw(float yaw)
    {
        info.yaw += yaw;
    }

    //</editor-fold>

    @Override
    public void drawShadow()
    {
        model.setPositionInfo(info);
        model.drawShadow();
    }
    
    @Override
    public void drawClick()
    {
        model.setPositionInfo(info);
        model.drawClick();
    }

    @Override
    public void onClick(int mouseKey)
    {
        throw new UnsupportedOperationException("Override in sub class.");
    }
}
