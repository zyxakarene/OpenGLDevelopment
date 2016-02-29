
package utils.constants;

import java.util.logging.Logger;


public class GameConstants
{
    public static final int GAME_WIDTH = (int) (1920 * 0.75);
    public static final int GAME_HEIGHT = (int) (1080 * 0.75);
    public static final String SKYBOX_MODEL = "Skybox";
    
    public static final Logger LOGGER = Logger.getGlobal();
    
    public static int SOUND_CHANNELS = 64;
    public static int FPS = 60;
    public static int MS_PER_FRAME = 1000/60; //16
    
    public static final boolean SHOW_ENEMY_PATH = false;
}
