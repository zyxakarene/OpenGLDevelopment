package game.control;

import java.util.ArrayList;
import utils.interfaces.IClickable;


public class ClickRegistrator
{
    private static final ArrayList<IClickable> clickables = new ArrayList<>();
    
    private static void register(IClickable clickable)
    {
        clickables.add(clickable);
    }

}
