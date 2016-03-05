package game.control.clicker;

import gl.shaders.ClickShader;
import org.lwjgl.util.vector.Vector3f;
import utils.interfaces.IClickable;
import utils.interfaces.IDrawable;

class ClickEntry implements IDrawable
{

    private static Vector3f usedColor;
    final float r, g, b;
    final int rgb;
    final int rInt, gInt, bInt;
    final IClickable clickable;

    public ClickEntry(IClickable clickable)
    {
        usedColor = ColorGenerator.getColor();
        r = usedColor.x;
        g = usedColor.y;
        b = usedColor.z;

        rInt = (int) (255 * r);
        gInt = (int) (255 * g);
        bInt = (int) (255 * b);

        rgb = ColorGenerator.toRgb(usedColor);

        this.clickable = clickable;
    }

    @Override
    public void draw()
    {
        ClickShader.shader().setClickColor(r, g, b);
        clickable.drawClick();
    }
    
    boolean matches(Vector3f clickColor)
    {
        return clickColor.x == rInt
                && clickColor.y == gInt
                && clickColor.z == bInt;
    }
}
