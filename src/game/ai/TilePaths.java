package game.ai;

import java.util.Arrays;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import utils.FloatMath;
import utils.constants.TileTypes;

public class TilePaths
{

    private static final Matrix4f TRANSFORM = new Matrix4f();
    
    private static final Vector3f[] LANE_CORNER_PATH = new Vector3f[]
    {
        new Vector3f(-2.5f, 0f, -0.245f),
        new Vector3f(0f, 0f, -0.245f),
        new Vector3f(0f, 2.5f, -0.245f)
    };
    
    private static final Vector3f[] LANE_STREIGHT_PATH = new Vector3f[]
    {
        new Vector3f(-2.5f, 0, -0.245f),
        new Vector3f(0, 0, -0.245f),
        new Vector3f(2.5f, 0, -0.245f)
    };
    
    private static final Vector3f[] LANE_RISE_PATH = new Vector3f[]
    {
        new Vector3f(-2.5f, 0, -4.245f),
        new Vector3f(-1.748f, 0, -3.873f),
        new Vector3f(1.748f, 0, -0.522f),
        new Vector3f(2.5f, 0, -0.245f)
    };

    public static Vector3f[] getPaths(int id)
    {
        Vector3f[] copy;
        Vector3f[] template;
        
        if (id == TileTypes.LANE_STRAIGHT)
        {
            template = LANE_STREIGHT_PATH;
            copy = new Vector3f[LANE_STREIGHT_PATH.length];
        }
        else if(id == TileTypes.LANE_CORNER)
        {
            template = LANE_CORNER_PATH;
            copy = new Vector3f[LANE_CORNER_PATH.length];
        }
        else if(id == TileTypes.LANE_RISE)
        {
            template = LANE_RISE_PATH;
            copy = new Vector3f[LANE_RISE_PATH.length];
        }
        else
        {
            throw new IllegalArgumentException("Invalid argument");
        }
        

        copyOver(template, copy);

        return copy;
    }

    private static void copyOver(Vector3f[] from, Vector3f[] to)
    {
        for (int i = 0; i < to.length; i++)
        {
            Vector3f vector3f = from[i];
            to[i] = new Vector3f(vector3f);
        }
    }

    public static void transform(Vector3f[] path, float x, float y, float z, float pitch, float yaw, float roll)
    {
        System.out.println("Before transform: " + Arrays.toString(path));
        
        TRANSFORM.setIdentity();
        TRANSFORM.translate(new Vector3f(x, y, z));
        TRANSFORM.rotate(FloatMath.toRadians(pitch), new Vector3f(1.0f, 0.0f, 0.0f));
        TRANSFORM.rotate(FloatMath.toRadians(roll), new Vector3f(0.0f, 1.0f, 0.0f));
        TRANSFORM.rotate(FloatMath.toRadians(yaw), new Vector3f(0.0f, 0.0f, 1.0f));
        
        Vector4f vec4;
        Vector3f vec3;
        
        for (int i = 0; i < path.length; i++)
        {
            vec3 = path[i];
            vec4 = new Vector4f(vec3.x, vec3.y, vec3.z, 1f);
            Matrix4f.transform(TRANSFORM, vec4, vec4);
            
            vec3.set(vec4.x, vec4.y, vec4.z);
        }
        
        System.out.println("After transform: " + Arrays.toString(path));
    }
}
