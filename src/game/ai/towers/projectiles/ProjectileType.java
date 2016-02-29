package game.ai.towers.projectiles;

import game.sound.Sounds;

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
    
    public Sounds getFlySound()
    {
        return ProjectileSoundManager.getFlySoundFrom(this);
    }
    
    public Sounds getHitSound()
    {
        return ProjectileSoundManager.getHitSoundFrom(this);
    }
    
    public Sounds getFireSound()
    {
        return ProjectileSoundManager.getFireSoundFrom(this);
    }
}
