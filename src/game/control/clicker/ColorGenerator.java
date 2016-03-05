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

    static int toRgb(int r, int g, int b)
    {
        return ((r & 0x0ff) << 16) | ((g & 0x0ff) << 8) | (b & 0x0ff);
    }

    static int toRgb(Vector3f colors)
    {
        return toRgb((int) colors.x, (int) colors.y, (int) colors.z);
    }
}
