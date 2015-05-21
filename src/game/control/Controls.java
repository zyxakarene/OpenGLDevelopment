package game.control;

import game.camera.Camera;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class Controls
{

    private static final Vector3f MOUSE_MOVEMENT = new Vector3f();
    
    public static void checkKeys()
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
            Display.destroy();
            Keyboard.destroy();
            Mouse.destroy();
            System.exit(0);
        }


        int dy = Mouse.getDY();
        int dx = Mouse.getDX();


        if (Mouse.isGrabbed())
        {
            MOUSE_MOVEMENT.set(-dy, 0, dx);
            Camera.rotate(MOUSE_MOVEMENT);
        }

        
        
        Camera.setIsFast(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT));
 
        if (Keyboard.isKeyDown(Keyboard.KEY_W))
        {
            Camera.move(Camera.FORWARD);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S))
        {
            Camera.move(Camera.BACKWARD);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D))
        {
            Camera.move(Camera.RIGHT);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A))
        {
            Camera.move(Camera.LEFT);
        }

        while (Keyboard.next())
        {
            if (Keyboard.getEventKeyState())
            {
                switch (Keyboard.getEventKey())
                {
                    case (Keyboard.KEY_Z):
                    {
                        Mouse.setGrabbed(!Mouse.isGrabbed());
                    }
                }
            }
        }
    }
}
