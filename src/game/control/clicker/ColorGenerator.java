package game.control.clicker;

import org.lwjgl.util.vector.Vector3f;

class ColorGenerator
{

    private static final Vector3f RESULT = new Vector3f();
    private static int lastRGB = 0;
    private static float r, g, b;

    static Vector3f getColor()
    {
        r = (lastRGB >> 16) & 0x000000FF;
        g = (lastRGB >> 8) & 0x000000FF;
        b = (lastRGB) & 0x000000FF;

        RESULT.set(r / 255f, g / 255f, b / 255f);

        lastRGB++;

        return RESULT;
    }
}
