package game.world.io;

import java.nio.ByteBuffer;
import utils.geometry.Point3D;
import utils.interfaces.IPositionable;

public class PathIO
{

    public static int BYTE_PER_NODE = 3 * (Float.SIZE / 8);

    static void savePath(ByteBuffer buffer, IPositionable position)
    {
        buffer.putFloat(position.getX());
        buffer.putFloat(position.getY());
        buffer.putFloat(position.getZ());
    }

    static IPositionable load(ByteBuffer buffer)
    {
        float x = buffer.getFloat();
        float y = buffer.getFloat();
        float z = buffer.getFloat();
        
        return new Point3D(x, y, z);
    }
}
