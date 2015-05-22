package game.control.clicker;

import gl.shaders.ShaderLoader;
import gl.shaders.ShaderType;
import java.util.ArrayList;
import utils.interfaces.IClickable;


public class ClickRegistrator
{
    private static final ArrayList<ClickEntry> clickables = new ArrayList<>();
    
    public static void register(IClickable clickable)
    {
        ClickEntry entry = new ClickEntry(clickable);
        clickables.add(entry);
    }

    public static void draw()
    {
        ShaderLoader.activateShader(ShaderType.CLICK);
        for (ClickEntry entry : clickables)
        {
            entry.draw();
        }
    }
    
}
