package game.world.basic;

import game.ai.debug.Marker;
import java.util.ArrayList;
import utils.FloatMath;
import utils.constants.GameConstants;
import utils.interfaces.IPositionable;
import utils.interfaces.IUpdateable;

public abstract class MovingEntity extends GameEntity implements IUpdateable
{

    private float degreesLeftRight;
    private float degreesUpDown;
    private int goingToIndex;
    private boolean isLookingAtPoint;
    private GameEntity[] markers;

    public MovingEntity()
    {
        goingToIndex = 0;
        isLookingAtPoint = false;
    }

    /**
     * Returns the path for this MovingEntity to go.
     * <p/>
     * @return The path to follow.
     */
    protected abstract ArrayList<IPositionable> getPath();

    /**
     * This is called when this MovingEntity has hit
     * the last point on its path.
     */
    protected abstract void hitEndPath();

    protected abstract void updateEntityRotations(float rightLeft, float upDown, boolean toTheLeft);

    protected final void recalculateAngle()
    {
        isLookingAtPoint = false;
    }

    @Override
    public void draw()
    {
        super.draw();

        if (GameConstants.SHOW_ENEMY_PATH && markers == null)
        {
            markers = new GameEntity[getPath().size()];

            for (int i = 0; i < markers.length; i++)
            {
                markers[i] = new Marker();
                markers[i].setScale(FloatMath.random() * 0.1f);
                markers[i].setPos(getPath().get(i).getX(), getPath().get(i).getY(), getPath().get(i).getZ());
            }
        }
        
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
        if (isAtTheEnd())
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
        IPositionable destination = getPath().get(goingToIndex);

        float lenght = getX() - destination.getX();
        float width = getY() - destination.getY();
        float height = getZ() - destination.getZ();

        if (Math.abs(width + lenght + height) <= 0.1)
        {
//            goingToIndex++;
//            lookAtPoint();
            return;
        }

        if (Math.abs(destination.getX() - getX()) <= 0.001)
        {
            if (destination.getY() > getY())
            {
                degreesLeftRight = 270;
            }
            else
            {
                degreesLeftRight = 90;
            }
        }
        else
        {
            float c = FloatMath.sqrt((lenght * lenght) + (width * width));
            float tempAngle = FloatMath.acos(((lenght * lenght) + (c * c) - (width * width)) / (2 * lenght * c));

            if (width < 0)
            {
                degreesLeftRight = FloatMath.toDegrees(tempAngle - (tempAngle * 2));
            }
            else
            {
                degreesLeftRight = FloatMath.toDegrees(tempAngle);
            }
        }

        if (degreesLeftRight > 360)
        {
            degreesLeftRight -= 360;
        }

        if (destination.getZ() == getZ())
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

        updateEntityRotations(degreesLeftRight, degreesUpDown, lenght > 0);

        isLookingAtPoint = true;
    }

    protected abstract float getHorizontalSpeed();

    protected abstract float getVerticalSpeed();

    protected abstract float getHitBias();

    private void moveTowardPoint(int elapsedTime)
    {
        float dx = FloatMath.cos(FloatMath.toRadians(degreesLeftRight + 180));
        float dy = FloatMath.sin(FloatMath.toRadians(degreesLeftRight + 180));
        float dZ = FloatMath.sin(FloatMath.toRadians(degreesUpDown));

        changeX(dx * elapsedTime * getHorizontalSpeed());
        changeY(dy * elapsedTime * getHorizontalSpeed());
        changeZ(dZ * elapsedTime * getVerticalSpeed());
    }

    private void checkHitPoint()
    {
        IPositionable destination = getPath().get(goingToIndex);

        float diffX = Math.abs(destination.getX() - getX());
        float diffY = Math.abs(destination.getY() - getY());
        float hitBias = getHitBias();

        if (diffX < hitBias && diffY < hitBias)
        {

//            System.out.println("Hit a point " + destination);
//            System.out.println("I'm at " + getX() + ", " + getY() + ", " + getZ());
//            if (goingToIndex + 1 < getPath().size())
//            {
//                System.out.println("Going to: " + getPath().get(goingToIndex + 1));
//            }
//            System.out.println("---");
            goingToIndex++;
            isLookingAtPoint = false;

            setX(destination.getX());
            setY(destination.getY());
            setZ(destination.getZ());

            if (goingToIndex >= getPath().size())
            {
                hitEndPath();
            }
        }
    }

    private boolean isAtTheEnd()
    {
        return goingToIndex >= getPath().size();
    }
}
