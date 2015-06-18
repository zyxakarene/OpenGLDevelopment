package game.sound;

import java.io.IOException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SoundLoader
{

    public static Audio getAudio(Sounds sound)
    {
        try
        {
            return getAudioInner(sound);
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Could not load resource.", ex);
        }
    }

    private static Audio getAudioInner(Sounds sound) throws IOException
    {
        return AudioLoader.getAudio(sound.type, ResourceLoader.getResourceAsStream(sound.path));
    }
}
