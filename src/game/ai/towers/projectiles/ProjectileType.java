package game.ai.towers.projectiles;

public enum ProjectileType
{

    ROCKET("Rocket", "Rocket", 5);
    
    public final String model;
    public final String texture;
    public final int damage;

    private ProjectileType(String model, String texture, int damage)
    {
        this.model = model;
        this.texture = texture;
        this.damage = damage;
    }
}
