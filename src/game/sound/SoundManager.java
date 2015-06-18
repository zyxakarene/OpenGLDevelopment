package game.sound;

import game.ai.enemies.Enemy;
import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.openal.Audio;
import utils.interfaces.IPositionable;


public class SoundManager
{

    private static ArrayList<SoundEntity> sounds;
    
    public static void init() throws IOException
    {
        sounds = new ArrayList<>();
    }
    
    public static void playSound(Sounds sound, IPositionable source)
    {
        if (sounds.size() > 32)
        {
            SoundEntity toBeRemoved = sounds.remove(0);
            toBeRemoved.stop();
        }
        
        Audio newAudio = SoundLoader.getAudio(sound);
        SoundEntity newSound = new SoundEntity(newAudio, source, sound);
        
        sounds.add(newSound);
    }

    public static void update(int elapsedTime)
    {
        for (SoundEntity soundEntity : sounds)
        {
            soundEntity.update(elapsedTime);
        }
    }

    public static void stopSoundFrom(IPositionable source)
    {
        SoundEntity entity;
        for (int i = 0; i < sounds.size(); i++)
        {
            entity = sounds.get(i);
            if (entity.stopIfFrom(source))
            {
                sounds.remove(i);
                i--;
            }
            
        }
    }
}
