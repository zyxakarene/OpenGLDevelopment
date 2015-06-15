package game.ai.enemies;

public enum EnemyType
{

    HUMANOID_ROBOT("Rocket", "Rocket", 0.1f, 100, false);
    
    public final String model;
    public final String texture;
    public final float speed;
    public final int health;
    public final boolean lookVertically;

    private EnemyType(String model, String texture, float speed, int health, boolean lookVertically)
    {
        this.model = model;
        this.texture = texture;
        this.speed = speed;
        this.health = health;
        this.lookVertically = lookVertically;
    }
}
