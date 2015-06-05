package game.world.basic;

import game.ai.debug.Marker;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import utils.FloatMath;
import utils.constants.GameConstants;
import utils.interfaces.IUpdateable;

public abstract class MovingEntity extends GameEntity implements IUpdateable
{

    private ArrayList<Vector3f> path;
    private float degreesUpDown;
    private int goingToIndex;
    private boolean isLookingAtPoint;
    private GameEntity[] markers;

    public MovingEntity()
    {
        path = getPath();

        goingToIndex = 0;
        isLookingAtPoint = false;

        if (GameConstants.SHOW_ENEMY_PATH)
        {
            markers = new GameEntity[path.size()];

            for (int i = 0; i < markers.length; i++)
            {
                markers[i] = new Marker();
                markers[i].setPos(path.get(i).x, path.get(i).y, path.get(i).z);
            }
        }
    }

    protected abstract ArrayList<Vector3f> getPath();

    @Override
    public void draw()
    {
        super.draw();

        if (GameConstants.SHOW_ENEMY_PATH)
        {
            for (int i = 0; i < markers.length; i++)
            {
                markers[i].draw();
            }
        }
    }

    @Override
    public void update(int elapsedTime)
    {
        if (goingToIndex == path.size())
        {
            return;
        }

        if (isLookingAtPoint == false)
        {
            lookAtPoint();
        }

        moveTowardPoint(elapsedTime);
        checkHitPoint();
    }

    private void lookAtPoint()
    {
        Vector3f destination = path.get(goingToIndex);

        float width = getY() - destination.y;
        float lenght = getX() - destination.x;
        float height = getZ() - destination.z;
        float newYaw;

        if (Math.abs(width + lenght + height) <= 0.1)
        {
            goingToIndex++;
            lookAtPoint();
            return;
        }

        if (Math.abs(destination.x - getX()) <= 0.001)
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

    private void moveTowardPoint(int elapsedTime)
    {
        float dx = FloatMath.cos(FloatMath.toRadians(getYaw()));
        float dy = FloatMath.sin(FloatMath.toRadians(getYaw()));
        float dZ = FloatMath.sin(FloatMath.toRadians(degreesUpDown));

        changeX(dx * elapsedTime * 0.01f);
        changeY(dy * elapsedTime * 0.01f);
        changeZ(dZ * elapsedTime * 0.013f);
    }

    private void checkHitPoint()
    {
        Vector3f destination = path.get(goingToIndex);

        float diffX = Math.abs(destination.x - getX());
        float diffY = Math.abs(destination.y - getY());

        if (diffX < 0.1f && diffY < 0.1f)
        {

//            System.out.println("Hit a point " + destination);
//            System.out.println("I'm at " + getX() + ", " + getY() + ", " + getZ());
//            if (goingToIndex + 1 < path.size())
//            {
//                System.out.println("Going to: " + path.get(goingToIndex + 1));
//            }
//            System.out.println("---");
            goingToIndex++;
            isLookingAtPoint = false;

            setX(destination.x);
            setY(destination.y);
            setZ(destination.z);
        }
    }
}
