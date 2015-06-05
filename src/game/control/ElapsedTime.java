package game.control;

public class ElapsedTime
{
    private static long lastUpdate = System.currentTimeMillis();
    private static int elapsedTime = 0;
    
    public static void update()
    {
        long newTime = System.currentTimeMillis();
        
        elapsedTime = (int) (newTime - lastUpdate);
        
        lastUpdate = newTime;
    }
    
    public static int get()
    {
        return elapsedTime;
    }
}
