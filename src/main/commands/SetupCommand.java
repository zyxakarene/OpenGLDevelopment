package main.commands;

import game.camera.Camera;
import game.control.clicker.ClickBuffer;
import gl.glUtils.GLUtils;
import gl.lighting.Shadow;
import gl.models.ModelManager;
import gl.shaders.ShaderLoader;
import gl.textures.TextureManager;
import java.io.IOException;
import java.net.URISyntaxException;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector3f;
import utils.constants.GameConstants;
import utils.exceptions.Msg;
import utils.exceptions.UncaughtExceptionHandlerImpl;
import utils.interfaces.ICommand;

public class SetupCommand implements ICommand
{

    @Override
    public void execute()
    {
        try
        {
            setupLogging();
            
            setupLwjgl();
            setupGameResources();
        }
        catch (LWJGLException | IOException | URISyntaxException ex)
        {
            Msg.error("Fatal exception in SetupCommand", ex);            
            System.exit(-1);
        }
    }

    private void setupLwjgl() throws LWJGLException
    {
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 2);
        contextAtrributes.withForwardCompatible(true);
        contextAtrributes.withProfileCore(true);

        Display.setDisplayMode(new DisplayMode(GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT));
        Display.create(pixelFormat, contextAtrributes);

        Keyboard.create();

        GLUtils.enableGLSettings();
    }

    private void setupGameResources() throws IOException, URISyntaxException
    {
        ShaderLoader.loadShaders();
        TextureManager.initTextures();
        ModelManager.loadAllTiles();
        ClickBuffer.init();
        
        Camera.create(new Vector3f(4f, 8f, -9f), new Vector3f(-90, 0, 0), GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
        
        Shadow.setup(GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
    }

    private void setupLogging() throws IOException
    {
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandlerImpl());
    }
}
