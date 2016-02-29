package game.sound;

public enum Sounds
{

    EXPLOSION    ("WAV", "audio/SFX_SML_EXPLOSION.wav", 2, false),
    ENEMY        ("WAV", "audio/pacman_chomp.wav", 2, true),
    ROCKET_LAUNCH("WAV", "audio/SFX_ROCKET_LAUNCHER.wav", 1, false),
    ROCKET_FLY   ("WAV", "audio/SFX_ROCKET_1.wav", 0.8f, true);
    
    public final String type;
    public final String path;
    public final float volume;
    public final boolean loop;

    private Sounds(String type, String path, float volume, boolean loop)
    {
        this.type = type;
        this.path = path;
        this.volume = volume;
        this.loop = loop;
    }
}
