package main;

import game.control.Controls;
import game.control.ElapsedTime;
import game.world.map.World;
import gl.glUtils.FPSCounter;
import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import main.commands.SetupCommand;
import utils.constants.GameConstants;

public class Main
{

    private static World world;

    public static void main(String[] args) throws LWJGLException, IOException
    {
        new SetupCommand().execute();
        mainLogic();
    }

    private static void mainLogic() throws LWJGLException, IOException
    {
        world = new World();

        while (!Display.isCloseRequested())
        {            
            Display.update();
            Display.sync(GameConstants.FPS);

            ElapsedTime.update();
            
            world.update();
            world.draw();

            FPSCounter.updateFPS();
            
            gl.glUtils.GLUtils.errorCheck();
            Controls.checkKeys();
        }
    }
}
