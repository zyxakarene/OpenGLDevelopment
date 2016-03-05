package game.control.clicker;

import game.control.MouseControl;
import gl.shaders.ShaderLoader;
import gl.shaders.ShaderType;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import utils.interfaces.IClickable;

public class ClickRegistrator
{

    private static final ArrayList<ClickEntry> clickables = new ArrayList<>(64);

    public static void register(IClickable clickable)
    {
        ClickEntry entry = new ClickEntry(clickable);
        clickables.add(entry);
    }

    public static void draw()
    {
        ShaderLoader.activateShader(ShaderType.CLICK);
        ClickBuffer.start();
        for (ClickEntry entry : clickables)
        {
            entry.draw();
        }
        ClickBuffer.end();
    }

    public static void checkClick()
    {
        if (MouseControl.wasLeftClicked())
        {
            wasClicked(1);
        }
        else if (MouseControl.wasMiddleClicked())
        {
            wasClicked(2);
        }
        else if (MouseControl.wasRightClicked())
        {
            wasClicked(3);
        }
        else if (MouseControl.wasSpecial1Clicked())
        {
            wasClicked(4);
        }
        else if (MouseControl.wasSpecial2Clicked())
        {
            wasClicked(5);
        }
    }

    private static void wasClicked(int keyId)
    {
        draw();

        Vector3f clickColor = ClickBuffer.checkClick();
        System.out.println("Clicked at " + clickColor);
        int color = ColorGenerator.toRgb(clickColor);
        if (color == 0xFFFFFF)
        {
            //Clicked white - Aka nothing
            return;
        }

        ClickEntry clickEntry = clickables.get(color);
        System.out.println(clickEntry);
        if (clickEntry != null)
        {
            clickEntry.clickable.onClick(keyId);
        }
    }
}
