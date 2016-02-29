package game.sound;

import java.io.IOException;
import java.util.LinkedList;
import org.newdawn.slick.openal.Audio;
import utils.constants.GameConstants;
import utils.interfaces.IPositionable;

public class SoundManager
{

    private static LinkedList<SoundEntity> availibleSounds;
    private static SoundEntity[] occupiedSounds;
    
    private static SoundEntity bufferEntity;

    public static void init() throws IOException
    {
        availibleSounds = new LinkedList<>();
        occupiedSounds = new SoundEntity[GameConstants.SOUND_CHANNELS];
        
        for (int i = 0; i < occupiedSounds.length; i++)
        {
            availibleSounds.add(new SoundEntity());
        }
    }

    public static void playSound(Sounds sound, IPositionable source)
    {
        if (availibleSounds.isEmpty())
        {
            String msg = "Warning: %s cannot play sound %s";
            System.out.println(String.format(msg, source, sound));
            return;
        }

        bufferEntity = availibleSounds.removeFirst();

        Audio newAudio = SoundLoader.getAudio(sound);
        bufferEntity.set(newAudio, source, sound);

        occupiedSounds[bufferEntity.id] = bufferEntity;
    }

    public static void update(int elapsedTime)
    {
        for (int i = 0; i < occupiedSounds.length; i++)
        {
            bufferEntity = occupiedSounds[i];
            if (bufferEntity != null)
            {
                bufferEntity.update(elapsedTime);
            }
        }
        
        bufferEntity = null;
    }

    public static void stopSoundFrom(IPositionable source)
    {
        for (int i = 0; i < occupiedSounds.length; i++)
        {
            bufferEntity = occupiedSounds[i];
            if (bufferEntity != null)
            {
                bufferEntity.stopIfFrom(source);
            }
        }
        
        bufferEntity = null;
    }

    static void onSoundDone(int id)
    {
        bufferEntity = occupiedSounds[id];
        availibleSounds.add(bufferEntity);
        
        occupiedSounds[id] = null;
        
        bufferEntity = null;
    }
}
