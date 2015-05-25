package game.control.clicker;

import game.camera.Camera;
import gl.glUtils.BufferControls;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import utils.constants.GameConstants;

public class ClickBuffer
{

    private static final Vector3f colorVec = new Vector3f();
    private static int texture;
    private static int frameBuffer;
    private static int renderBuffer;

    public static void init()
    {
        frameBuffer = BufferControls.generateFrameBuffer();
        texture = BufferControls.generateTexture();
        renderBuffer = BufferControls.generateRenderBuffer();

        BufferControls.activeTexture(1);
        BufferControls.bindFrameBuffer(frameBuffer);
        BufferControls.bindTexture(texture);

        BufferControls.frameBufferToColor(frameBuffer, renderBuffer, texture);
    }

    static void start()
    {
        GL11.glViewport(0, 0, 128, 128);
        BufferControls.bindTexture(0);

        BufferControls.bindFrameBuffer(frameBuffer);
        Camera.clearViewWhite();
    }

    static void end()
    {
        GL11.glViewport(0, 0, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
        BufferControls.bindFrameBuffer(0);
    }

    public static void bindTexture()
    {
        BufferControls.activeTexture(1);
        BufferControls.bindTexture(texture);
    }

    static Vector3f checkClick()
    {
        BufferControls.bindFrameBuffer(frameBuffer);
        BufferControls.activeTexture(1);
        BufferControls.bindTexture(texture);

        ByteBuffer bb = BufferUtils.createByteBuffer(12);
        if (Mouse.isGrabbed())
        {
            GL11.glReadPixels(64, 64, 1, 1, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, bb);
        }
        else
        {
            float percentX = (float)Mouse.getX() / (float)GameConstants.GAME_WIDTH;
            float percentY = (float)Mouse.getY() / (float)GameConstants.GAME_HEIGHT;
            
            GL11.glReadPixels((int)(128 * percentX), (int)(128 * percentY), 1, 1, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, bb);
        }
        int r = bb.get();
        int g = bb.get();
        int b = bb.get();
        
        r = r < 0 ? r + 256 : r;
        g = g < 0 ? g + 256 : g;
        b = b < 0 ? b + 256 : b;

        BufferControls.bindFrameBuffer(0);
        BufferControls.activeTexture(0);
        BufferControls.bindTexture(0);
        
        colorVec.set(r, g, b);
        return colorVec;
    }
}
