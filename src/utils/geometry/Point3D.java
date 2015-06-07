package utils.geometry;

import utils.interfaces.IPositionable;

public class Point3D implements IPositionable
{

    private float x, y, z;

    public Point3D(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString()
    {
        return "Point3D{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }

    public Point3D(IPositionable pos)
    {
        x = pos.getX();
        y = pos.getY();
        z = pos.getZ();
    }

    @Override
    public float getX()
    {
        return x;
    }

    @Override
    public float getY()
    {
        return y;
    }

    @Override
    public float getZ()
    {
        return z;
    }

    @Override
    public void setX(float x)
    {
        this.x = x;
    }

    @Override
    public void setY(float y)
    {
        this.y = y;
    }

    @Override
    public void setZ(float z)
    {
        this.z = z;
    }
}
