package game.control;

import org.lwjgl.input.Mouse;

public class MouseControl
{

    private static boolean clickedLeft;
    private static boolean clickedRight;
    private static boolean clickedMiddle;
    private static boolean clickedSpecial1;
    private static boolean clickedSpecial2;
    private static int dX, dY;

    public static boolean wasLeftClicked()
    {
        return clickedLeft;
    }

    public static boolean wasRightClicked()
    {
        return clickedRight;
    }

    public static boolean wasMiddleClicked()
    {
        return clickedMiddle;
    }

    public static boolean wasSpecial1Clicked()
    {
        return clickedSpecial1;
    }

    public static boolean wasSpecial2Clicked()
    {
        return clickedSpecial2;
    }

    public static int getMovementX()
    {
        return dX;
    }

    public static int getMovementY()
    {
        return dY;
    }

    public static void check()
    {
        resetData();
        checkNewClicks();
        checkMovements();
    }

    private static void resetData()
    {
        clickedLeft = clickedRight = clickedMiddle =
        clickedSpecial1 = clickedSpecial2 = false;
        
        dX = dY = 0;
    }

    private static void checkNewClicks()
    {
        while (Mouse.next())
        {
            if (Mouse.getEventButtonState())
            {
                switch (Mouse.getEventButton())
                {
                    case (0):
                    {
                        clickedLeft = true;
                        break;
                    }
                    case (1):
                    {
                        clickedMiddle = true;
                        break;
                    }
                    case (2):
                    {
                        clickedRight = true;
                        break;
                    }
                    case (3):
                    {
                        clickedSpecial1 = true;
                        break;
                    }
                    case (4):
                    {
                        clickedSpecial2 = true;
                        break;
                    }
                }
            }
        }
    }

    private static void checkMovements()
    {
        dX = Mouse.getDX();
        dY = Mouse.getDY();
    }
}
