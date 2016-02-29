package game.world.basic;


public class UiPositioning
{

    static UiPositioning getDefault()
    {
        return new UiPositioning();
    }
    
    public int x = 0, y = 0;
    public int width = 0, height = 0;
}
