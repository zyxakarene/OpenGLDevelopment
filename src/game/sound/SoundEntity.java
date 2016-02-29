package game.sound;

import gl.shaders.SharedShaderObjects;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.openal.Audio;
import utils.interfaces.IPositionable;

class SoundEntity
{

    private static final Vector4f SHARED_VECTOR = new Vector4f();
    private Audio sound;
    private IPositionable source;
    private Sounds soundType;
    private float timePlayed;
    private boolean stopped;
    
    private static int counter = 0;
    final int id;

    SoundEntity()
    {
        id = counter;
        counter++;
    }
    
    void set(Audio sound, IPositionable source, Sounds soundType)
    {
        this.sound = sound;
        this.source = source;
        this.soundType = soundType;
        
        timePlayed = 0;
        stopped = false;
    }

    void update(int elapsedTime)
    {
        if (stopped)
        {
            return;
        }

        SHARED_VECTOR.set(source.getX(), source.getY(), source.getZ(), 1);
        Matrix4f.transform(SharedShaderObjects.SHARED_VIEW_TRANSFORM, SHARED_VECTOR, SHARED_VECTOR);
        float time = 0;

        if (sound.isPlaying())
        {
            time = sound.getPosition();

            if (soundType.loop == false && time < timePlayed)
            {
                stop();
            }

            sound.stop();
        }

        sound.playAsSoundEffect(1, soundType.volume, false, SHARED_VECTOR.x, SHARED_VECTOR.y, SHARED_VECTOR.z);
        sound.setPosition(time);
                
        timePlayed += elapsedTime;
    }

    private void stop()
    {
        stopped = true;
        if (sound.isPlaying())
        {
            sound.stop();
        }
        
        SoundManager.onSoundDone(id);
    }

    void stopIfFrom(IPositionable source)
    {
        if (this.source == source)
        {
            stop();
        }
    }
}
