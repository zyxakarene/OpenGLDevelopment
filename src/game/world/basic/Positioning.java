package game.world.basic;


public class Positioning
{

    static Positioning getDefault()
    {
        return new Positioning();
    }
    
    public float scale = 0.1f;
    public float x = 0, y = 0, z = 0;
    public float pitch = 0, roll = 0, yaw = 0;
}
