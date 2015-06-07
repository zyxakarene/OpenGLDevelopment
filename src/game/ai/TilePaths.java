package game.ai;

import java.util.Arrays;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import utils.FloatMath;
import utils.constants.TileTypes;
import utils.geometry.Point3D;
import utils.interfaces.IPositionable;

public class TilePaths
{

    private static final Matrix4f TRANSFORM = new Matrix4f();
    
    private static final IPositionable[] LANE_CORNER_PATH = new IPositionable[]
    {
        new Point3D(-2.5f, 0f, -0.245f),
        new Point3D(0f, 0f, -0.245f),
        new Point3D(0f, 2.5f, -0.245f)
    };
    
    private static final IPositionable[] LANE_STREIGHT_PATH = new IPositionable[]
    {
        new Point3D(-2.5f, 0, -0.245f),
        new Point3D(0, 0, -0.245f),
        new Point3D(2.5f, 0, -0.245f)
    };
    
    private static final IPositionable[] LANE_RISE_PATH = new IPositionable[]
    {
        new Point3D(-2.5f, 0, -4.245f),
        new Point3D(-1.748f, 0, -3.873f),
        new Point3D(1.748f, 0, -0.522f),
        new Point3D(2.5f, 0, -0.245f)
    };

    public static IPositionable[] getPaths(int id)
    {
        IPositionable[] copy;
        IPositionable[] template;
        
        if (id == TileTypes.LANE_STRAIGHT)
        {
            template = LANE_STREIGHT_PATH;
            copy = new IPositionable[LANE_STREIGHT_PATH.length];
        }
        else if(id == TileTypes.LANE_CORNER)
        {
            template = LANE_CORNER_PATH;
            copy = new IPositionable[LANE_CORNER_PATH.length];
        }
        else if(id == TileTypes.LANE_RISE)
        {
            template = LANE_RISE_PATH;
            copy = new IPositionable[LANE_RISE_PATH.length];
        }
        else
        {
            throw new IllegalArgumentException("Invalid argument");
        }
        

        copyOver(template, copy);

        return copy;
    }

    private static void copyOver(IPositionable[] from, IPositionable[] to)
    {
        for (int i = 0; i < to.length; i++)
        {
            IPositionable vector3f = from[i];
            to[i] = new Point3D(vector3f);
        }
    }

    public static void transform(IPositionable[] path, float x, float y, float z, float pitch, float yaw, float roll)
    {
        System.out.println("Before transform: " + Arrays.toString(path));
        
        TRANSFORM.setIdentity();
        TRANSFORM.translate(new Vector3f(x, y, z));
        TRANSFORM.rotate(FloatMath.toRadians(pitch), new Vector3f(1.0f, 0.0f, 0.0f));
        TRANSFORM.rotate(FloatMath.toRadians(roll), new Vector3f(0.0f, 1.0f, 0.0f));
        TRANSFORM.rotate(FloatMath.toRadians(yaw), new Vector3f(0.0f, 0.0f, 1.0f));
        
        Vector4f vec4;
        IPositionable pathPoint;
        
        for (int i = 0; i < path.length; i++)
        {
            pathPoint = path[i];
            vec4 = new Vector4f(pathPoint.getX(), pathPoint.getY(), pathPoint.getZ(), 1f);
            Matrix4f.transform(TRANSFORM, vec4, vec4);
            
            pathPoint.setX(vec4.x);
            pathPoint.setY(vec4.y);
            pathPoint.setZ(vec4.z);
        }
        
        System.out.println("After transform: " + Arrays.toString(path));
    }
}
