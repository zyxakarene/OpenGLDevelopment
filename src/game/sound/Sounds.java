package game.sound;

public enum Sounds
{

    EXPLOSION    ("WAV", "audio/SFX_SML_EXPLOSION.wav", 3, false),
    ENEMY        ("WAV", "audio/pacman_chomp.wav", 3, true),
    ROCKET_LAUNCH("WAV", "audio/SFX_ROCKET_LAUNCHER.wav", 1, false),
    ROCKET_FLY   ("WAV", "audio/SFX_ROCKET_1.wav", 1, true);
    
    public final String type;
    public final String path;
    public final float volume;
    public final boolean loop;

    private Sounds(String type, String path, int volume, boolean loop)
    {
        this.type = type;
        this.path = path;
        this.volume = volume;
        this.loop = loop;
    }
}
