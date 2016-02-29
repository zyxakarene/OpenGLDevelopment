package main;

import game.control.ElapsedTime;
import game.control.KeyboardControl;
import game.control.MouseControl;
import game.state.GameStateManager;
import gl.glUtils.FPSCounter;
import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import main.commands.SetupCommand;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.openal.SoundStore;
import utils.constants.GameConstants;

public class Main
{

    private static int spilloverMs;

    public static void main(String[] args) throws LWJGLException, IOException
    {
        new SetupCommand().execute();
        mainLogic();
    }

    private static void mainLogic() throws LWJGLException, IOException
    {
        GameStateManager.instance.setPlayState();

        while (!Display.isCloseRequested())
        {
            Display.update();
            Display.sync(GameConstants.FPS);

            MouseControl.check();
            KeyboardControl.checkKeys();

            update();
            GameStateManager.instance.draw();

            FPSCounter.updateFPS();

            gl.glUtils.GLUtils.errorCheck();

            checkShutDown();
        }
    }

    private static void checkShutDown()
    {
        if (KeyboardControl.wasKeyPressed(Keyboard.KEY_ESCAPE))
        {
            Display.destroy();
            Keyboard.destroy();
            Mouse.destroy();
            System.exit(0);
        }
    }

    private static void update()
    {
        ElapsedTime.update();
        int elapsedTime = ElapsedTime.get();
        int mspPerFrame = GameConstants.MS_PER_FRAME;

        int parts = elapsedTime / mspPerFrame;
        spilloverMs += elapsedTime - (mspPerFrame * parts);
        while (spilloverMs >= mspPerFrame)
        {
            parts++;
            spilloverMs -= mspPerFrame;
        }
        for (int i = 0; i < parts; i++)
        {
            GameStateManager.instance.update(mspPerFrame);
        }

        SoundStore.get().poll(elapsedTime);
    }
}
