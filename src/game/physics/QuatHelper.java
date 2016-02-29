package game.physics;

import javax.vecmath.Quat4f;
import utils.FloatMath;

public class QuatHelper
{

    private static float helper;

    public static float getPitch(Quat4f q)
    {
        return (float) FloatMath.atan((2 * ((q.x * q.y) + (q.z * q.w))) / (1 - (2 * ((q.y * q.y) + (q.z * q.z)))));
    }

    public static float getYaw(Quat4f q)
    {
        return (float) (Math.asin(-2.0 * (q.x * q.z - q.w * q.y)));
    }

    public static float getRoll(Quat4f q)
    {
        return (float) (Math.atan2(2.0 * (q.x * q.y + q.w * q.z), q.w * q.w + q.x * q.x - q.y * q.y - q.z * q.z));
    }
}
