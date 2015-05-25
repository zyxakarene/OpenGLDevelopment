package game.ai;

import game.control.ElapsedTime;
import game.world.basic.GameEntity;
import game.world.map.Tile;
import main.devGui.ControlGui;
import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector3f;
import utils.FloatMath;
import utils.constants.EnemyTypes;
import utils.constants.TextureConstants;
import utils.interfaces.IUpdateable;

public class Enemy extends GameEntity implements IUpdateable
{

    private static Vector3f[] path;

    static
    {
        path = new Vector3f[34];

        path[0] = new Vector3f(0 * Tile.WIDTH, 2 * Tile.WIDTH, 0 * Tile.HEIGHT);
        path[1] = new Vector3f(1 * Tile.WIDTH, 2 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[2] = new Vector3f(2 * Tile.WIDTH, 2 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[3] = new Vector3f(2 * Tile.WIDTH, 1 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[4] = new Vector3f(2 * Tile.WIDTH, 0 * Tile.WIDTH, 0 * Tile.HEIGHT);
        path[5] = new Vector3f(5 * Tile.WIDTH, 0 * Tile.WIDTH, 0 * Tile.HEIGHT);
        path[6] = new Vector3f(5 * Tile.WIDTH, 1 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[7] = new Vector3f(5 * Tile.WIDTH, 4 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[8] = new Vector3f(4 * Tile.WIDTH, 4 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[9] = new Vector3f(4 * Tile.WIDTH, 5 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[10] = new Vector3f(4 * Tile.WIDTH, 7 * Tile.WIDTH, -1 * Tile.HEIGHT);
        path[11] = new Vector3f(4 * Tile.WIDTH, 8 * Tile.WIDTH, -1 * Tile.HEIGHT);
        path[12] = new Vector3f(6 * Tile.WIDTH, 8 * Tile.WIDTH, -1 * Tile.HEIGHT);
        path[13] = new Vector3f(8 * Tile.WIDTH, 8 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[14] = new Vector3f(10 * Tile.WIDTH, 8 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[15] = new Vector3f(10 * Tile.WIDTH, 3 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[16] = new Vector3f(10 * Tile.WIDTH, 2 * Tile.WIDTH, 0 * Tile.HEIGHT);
        path[17] = new Vector3f(7 * Tile.WIDTH, 2 * Tile.WIDTH, 0 * Tile.HEIGHT);
        path[18] = new Vector3f(7 * Tile.WIDTH, 0 * Tile.WIDTH, 0 * Tile.HEIGHT);
        path[19] = new Vector3f(8 * Tile.WIDTH, 0 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[20] = new Vector3f(11 * Tile.WIDTH, 0 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[21] = new Vector3f(12 * Tile.WIDTH, 0 * Tile.WIDTH, 0 * Tile.HEIGHT);
        path[22] = new Vector3f(14 * Tile.WIDTH, 0 * Tile.WIDTH, 0 * Tile.HEIGHT);
        path[23] = new Vector3f(14 * Tile.WIDTH, 5 * Tile.WIDTH, 0 * Tile.HEIGHT);
        path[24] = new Vector3f(13 * Tile.WIDTH, 5 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[25] = new Vector3f(12 * Tile.WIDTH, 5 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[26] = new Vector3f(12 * Tile.WIDTH, 8 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[27] = new Vector3f(14 * Tile.WIDTH, 8 * Tile.WIDTH, 1 * Tile.HEIGHT);
        path[28] = new Vector3f(15 * Tile.WIDTH, 8 * Tile.WIDTH, 2 * Tile.HEIGHT);
        path[29] = new Vector3f(18 * Tile.WIDTH, 8 * Tile.WIDTH, 2 * Tile.HEIGHT);
        path[30] = new Vector3f(18 * Tile.WIDTH, 7 * Tile.WIDTH, 2 * Tile.HEIGHT);
        path[31] = new Vector3f(18 * Tile.WIDTH, 3 * Tile.WIDTH, -2 * Tile.HEIGHT);
        path[32] = new Vector3f(18 * Tile.WIDTH, 1 * Tile.WIDTH, -2 * Tile.HEIGHT);
        path[33] = new Vector3f(19 * Tile.WIDTH, 1 * Tile.WIDTH, -2 * Tile.HEIGHT);
    }
    private float degreesUpDown;
    private int goingToIndex;
    private boolean isLookingAtPoint;

    public Enemy()
    {
        setModel(EnemyTypes.TYPE_ONES_NAME);
        setTexture(TextureConstants.ENEMY_SET);

        goingToIndex = 0;
        isLookingAtPoint = false;

//        new ControlGui(this).setVisible(true);
    }

    @Override
    public void onClick(int mouseKey)
    {
    }

    @Override
    public void update()
    {
        if (goingToIndex == path.length)
        {
            return;
        }

        if (isLookingAtPoint == false)
        {
            lookAtPoint();
        }

        moveTowardPoint();
        checkHitPoint();
    }

    private void lookAtPoint()
    {
        Vector3f destination = path[goingToIndex];

        float width = getY() - destination.y;
        float lenght = getX() - destination.x;
        float height = getZ() - destination.z;
        float newYaw;

        if (Math.abs(destination.x - getX()) <= 2)
        {
            if (destination.y > getY())
            {
                newYaw = 270;
            }
            else
            {
                newYaw = 90;
            }
        }
        else
        {
            float c = FloatMath.sqrt((lenght * lenght) + (width * width));
            float tempAngle = FloatMath.acos(((lenght * lenght) + (c * c) - (width * width)) / (2 * lenght * c));

            if (width < 0)
            {
                newYaw = FloatMath.toDegrees(tempAngle - (tempAngle * 2));
            }
            else
            {
                newYaw = FloatMath.toDegrees(tempAngle);
            }
        }

        if (destination.z == getZ())
        {
            degreesUpDown = 0;
        }
        else
        {
            float otherSide = lenght == 0 ? width : lenght;
            
            float hyp = FloatMath.sqrt((otherSide * otherSide) + (height * height));
            float tempAngle = FloatMath.acos(((otherSide * otherSide) + (hyp * hyp) - (height * height)) / (2 * otherSide * hyp));
            
            if (height < 0)
            {
                degreesUpDown = FloatMath.toDegrees(tempAngle - (tempAngle * 2)) + 180;
            }
            else
            {
                degreesUpDown = FloatMath.toDegrees(tempAngle) + 180;
            }
        }

        setYaw(newYaw + 180);
        isLookingAtPoint = true;
    }

    private void moveTowardPoint()
    {
        float dx = FloatMath.cos(FloatMath.toRadians(getYaw()));
        float dy = FloatMath.sin(FloatMath.toRadians(getYaw()));
        float dZ = FloatMath.sin(FloatMath.toRadians(degreesUpDown));


        int elapsedTime = ElapsedTime.get();

        changeX(dx * elapsedTime * 0.01f);
        changeY(dy * elapsedTime * 0.01f);
        changeZ(dZ * elapsedTime * 0.013f);
    }

    private void checkHitPoint()
    {
        Vector3f destination = path[goingToIndex];

        float diffX = Math.abs(destination.x - getX());
        float diffY = Math.abs(destination.y - getY());

        if (diffX < 0.1f && diffY < 0.1f)
        {

            System.out.println("Hit a point " + destination);
            System.out.println("I'm at " + getX() + ", " + getY() + ", " + getZ());
            System.out.println("---");
            goingToIndex++;
            isLookingAtPoint = false;

            setX(destination.x);
            setY(destination.y);
            setZ(destination.z);
        }
    }
}
