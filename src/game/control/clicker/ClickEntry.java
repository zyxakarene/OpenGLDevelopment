package game.control.clicker;

import gl.shaders.ClickShader;
import org.lwjgl.util.vector.Vector3f;
import utils.interfaces.IClickable;
import utils.interfaces.IDrawable;

class ClickEntry implements IDrawable
{

    private static Vector3f usedColor;
    
    final float r, g, b;
    final IClickable clickable;

    public ClickEntry(IClickable clickable)
    {
        usedColor = ColorGenerator.getColor();
        r = usedColor.x;
        g = usedColor.y;
        b = usedColor.z;

        this.clickable = clickable;
    }

    @Override
    public void draw()
    {
        ClickShader.shader().setClickColor(r, g, b);
        clickable.drawClick();
    }
}
