package game.camera;

import game.control.ElapsedTime;
import game.control.KeyboardControl;
import game.control.MouseControl;
import gl.shaders.ClickShader;
import gl.shaders.LineShader;
import gl.shaders.PhysShader;
import gl.shaders.SharedShaderObjects;
import gl.shaders.ShaderLoader;
import gl.shaders.ShaderType;
import gl.shaders.SkyboxShader;
import gl.shaders.TransformShader;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Matrix4f;
import utils.FloatMath;

import static org.lwjgl.opengl.GL11.*;
import utils.geometry.Point3D;
import utils.interfaces.IPositionable;

public class Camera
{

    private static final Matrix4f SHARED_VIEW_TRANSFORM = SharedShaderObjects.SHARED_VIEW_TRANSFORM;
    private static final IPositionable POSITION = new Point3D(0, 0, 0);
    
    public static final int FORWARD = 1;
    public static final int BACKWARD = 2;
    public static final int RIGHT = 3;
    public static final int LEFT = 4;
    public static final int PITCH = 1;
    public static final int YAW = 2;
    public static final int ROLL = 3;
    private static float x, y, z;
    private static float pitch, yaw, roll;
    private static float moveSpeed = 0.2f;
    private static float orthoZoom = 1f;
    private static float screenWidth;
    private static float screenHeight;
    private static boolean isPerspective;
    private static int shakeTimer;
    private static float shakeZ;

    public static void create(Vector3f pos, int height, int width)
    {
        create(pos, new Vector3f(), height, width);
    }

    public static Vector3f getDir()
    {
        float dX = FloatMath.sin(FloatMath.toRadians(yaw)) * FloatMath.cos(FloatMath.toRadians(pitch + 90));
        float dY = FloatMath.cos(FloatMath.toRadians(yaw)) * FloatMath.cos(FloatMath.toRadians(pitch + 90));
        float dZ = FloatMath.cos(FloatMath.toRadians(pitch));

        Vector3f f = new Vector3f(dX, dY, dZ);
        return f;
    }

    public static void create(Vector3f pos, Vector3f angle, float aWidth, float aHeight)
    {
        x = pos.x;
        y = pos.y;
        z = pos.z;
        pitch = angle.x;
        roll = angle.y;
        yaw = angle.z;

        screenWidth = aWidth;
        screenHeight = aHeight;

        Matrix4f projectionView = perspective(70f, screenWidth / screenHeight, 0.01f, 2f);
        updateProjection(projectionView);

        KeyboardControl.listenForHolding(Keyboard.KEY_W);
        KeyboardControl.listenForHolding(Keyboard.KEY_S);
        KeyboardControl.listenForHolding(Keyboard.KEY_A);
        KeyboardControl.listenForHolding(Keyboard.KEY_D);
        KeyboardControl.listenForHolding(Keyboard.KEY_LSHIFT);
    }

    private static void move(int direction)
    {
        float multiplier = ElapsedTime.get() * 0.15f;

        float dX = FloatMath.sin(FloatMath.toRadians(yaw)) * FloatMath.cos(FloatMath.toRadians(pitch + 90)) * moveSpeed * multiplier;
        float dY = FloatMath.cos(FloatMath.toRadians(yaw)) * FloatMath.cos(FloatMath.toRadians(pitch + 90)) * moveSpeed * multiplier;
        float dZ = FloatMath.cos(FloatMath.toRadians(pitch)) * moveSpeed * multiplier;

        switch (direction)
        {
            case (FORWARD):
            {
                x = x - dX;
                y = y - dY;
                z = z + dZ;
                break;
            }
            case (BACKWARD):
            {
                x = x + dX;
                y = y + dY;
                z = z - dZ;
                break;
            }
            case (RIGHT):
            {
                dX = FloatMath.sin(FloatMath.toRadians(yaw + 90)) * moveSpeed * multiplier;
                dY = FloatMath.cos(FloatMath.toRadians(yaw + 90)) * moveSpeed * multiplier;;

                x = x - dX;
                y = y - dY;
                break;
            }
            case (LEFT):
            {
                dX = FloatMath.sin(FloatMath.toRadians(yaw - 90)) * moveSpeed * multiplier;
                dY = FloatMath.cos(FloatMath.toRadians(yaw - 90)) * moveSpeed * multiplier;

                x = x - dX;
                y = y - dY;
                break;
            }
        }
    }

    private static void rotate(float x, float y, float z)
    {
        float multiplier = ElapsedTime.get() * 0.008f;

        pitch = pitch + (x * multiplier);
        roll = roll + (y * multiplier);
        yaw = yaw + (z * multiplier);

        if (pitch > 0)
        {
            pitch = 0;
        }
        else if (pitch < -180)
        {
            pitch = -180;
        }

        if (yaw > 360)
        {
            yaw = yaw - 360;
        }
        else if (yaw < 0)
        {
            yaw = yaw + 360;
        }
    }

    public static void look()
    {
        updateViewRotation();

        ShaderLoader.activateShader(ShaderType.SKYBOX);
        SkyboxShader.shader().updateViewUniform();

        updateViewTranslation();

        ShaderLoader.activateShader(ShaderType.TRANSFORM);
        TransformShader.shader().updateViewUniform();
        TransformShader.shader().setViewPos(-x, -y, -z);

        ShaderLoader.activateShader(ShaderType.PHYS);
        PhysShader.shader().updateViewUniform();
        PhysShader.shader().setViewPos(-x, -y, -z);
        
        ShaderLoader.activateShader(ShaderType.CLICK);
        ClickShader.shader().updateViewUniform();
        
        ShaderLoader.activateShader(ShaderType.LINE);
        LineShader.shader().updateViewUniform();




//        if (!isPerspective)
//        {
//            int wheel = Mouse.getDWheel();
//            if (wheel > 0)
//            {
//                orthoZoom *= 0.9f;
//            }
//            else if (wheel < 0)
//            {
//                orthoZoom *= 1.1f;
//            }
//        }
//
//        if (Mouse.isButtonDown(0))
//        {
//            Matrix4f projectionView = perspective(70f, screenWidth / screenHeight, 0.01f, 2f);
//            updateProjection(projectionView);
//        }
//        else if (Mouse.isButtonDown(1))
//        {
//            Matrix4f projectionView = orthographic(screenWidth * orthoZoom, screenHeight * orthoZoom, 0.01f, 100f);
//            updateProjection(projectionView);
//        }
    }

    private static void updateProjection(Matrix4f projection)
    {
        ShaderLoader.activateShader(ShaderType.CLICK);
        ClickShader.shader().setupProjection(projection);

        ShaderLoader.activateShader(ShaderType.TRANSFORM);
        TransformShader.shader().setupProjection(projection);
        
        ShaderLoader.activateShader(ShaderType.PHYS);
        PhysShader.shader().setupProjection(projection);

        ShaderLoader.activateShader(ShaderType.SKYBOX);
        SkyboxShader.shader().setupProjection(projection);
        
        ShaderLoader.activateShader(ShaderType.LINE);
        LineShader.shader().setupProjection(projection);
    }

    private static Matrix4f perspective(float fov, float aspect, float near, float far)
    {
        far *= 30;
        isPerspective = true;

        Matrix4f m = new Matrix4f();

        float angle = (fov / 180.0f) * FloatMath.PI;
        float f = 1.0f / FloatMath.tan(angle * 0.5f);

        /* Note, matrices are accessed like 2D arrays in C.
         They are column major, i.e m[y][x] */

        m.m00 = f / aspect;
        m.m11 = f;
        m.m22 = (far + near) / (near - far);
        m.m23 = -1.0f;
        m.m32 = (2.0f * far * near) / (near - far);
        return m;
    }

    private static Matrix4f orthographic(float width, float height, float near, float far)
    {
        isPerspective = false;

        float scale = 50f;
        float left = -width / scale;
        float right = width / scale;
        float top = height / scale;
        float bottom = -height / scale;

        Matrix4f OrthoMatrix = new Matrix4f();

        OrthoMatrix.m00 = 2.0f / (right - left);
        OrthoMatrix.m11 = 2.0f / (top - bottom);
        OrthoMatrix.m22 = -2.0f / (far - near);

        OrthoMatrix.m30 = -(right + left) / (right - left);
        OrthoMatrix.m31 = -(top + bottom) / (top - bottom);
        OrthoMatrix.m32 = -(far + near) / (far - near);
        OrthoMatrix.m33 = 1.0f;

        return OrthoMatrix;
    }

    private static void updateViewTranslation()
    {
        SHARED_VIEW_TRANSFORM.translate(new Vector3f(x, y, z));

        float dX = FloatMath.sin(FloatMath.toRadians(yaw)) * FloatMath.cos(FloatMath.toRadians(pitch + 90)) * 0.1f;
        float dY = FloatMath.cos(FloatMath.toRadians(yaw)) * FloatMath.cos(FloatMath.toRadians(pitch + 90)) * 0.1f;
        float dZ = FloatMath.cos(FloatMath.toRadians(pitch + shakeZ)) * 0.1f;
        SHARED_VIEW_TRANSFORM.translate(new Vector3f(-dX * 10, -dY * 10, dZ * 10));
    }

    private static void updateViewRotation()
    {
        SHARED_VIEW_TRANSFORM.setIdentity();
        SHARED_VIEW_TRANSFORM.rotate(FloatMath.toRadians(pitch), new Vector3f(1, 0, 0));
        SHARED_VIEW_TRANSFORM.rotate(FloatMath.toRadians(roll), new Vector3f(0, 1, 0));
        SHARED_VIEW_TRANSFORM.rotate(FloatMath.toRadians(yaw), new Vector3f(0, 0, 1));
    }

    public static float getX()
    {
        return x;
    }

    public static float getY()
    {
        return y;
    }

    public static float getZ()
    {
        return z;
    }
    
    public static IPositionable position()
    {
        POSITION.setPos(-x, -y, -z);
        return POSITION;
    }

    public static void clearViewWhite()
    {
        glClearColor(1.0f, 1.0f, 1.0f, 0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void clearView()
    {
        glClearColor(0.4f, 0.6f, 0.9f, 0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void clearDepth()
    {
        glClear(GL_DEPTH_BUFFER_BIT);
    }

    private static void setIsFast(boolean keyDown)
    {
        moveSpeed = keyDown ? 1 : 0.2f;
    }
    
    public static void shakeFor(int shakeTimeInMs)
    {
        if (shakeTimer <= shakeTimeInMs)
        {
            shakeTimer = shakeTimeInMs;
        }
    }

    public static void update(int elapsedTime)
    {
        if (shakeTimer > 0)
        {
            shakeTimer -= elapsedTime;
            if (shakeTimer <= 0)
            {
                shakeTimer = 0;
            }
            
            shakeZ = FloatMath.sin(shakeTimer) * 3;
        }

        if (KeyboardControl.wasKeyPressed(Keyboard.KEY_Z))
        {
            Mouse.setGrabbed(!Mouse.isGrabbed());
        }

        if (Mouse.isGrabbed())
        {
            int dx = MouseControl.getMovementX();
            int dy = MouseControl.getMovementY();
            rotate(-dy, 0, dx);
        }

        setIsFast(KeyboardControl.isKeyDown(Keyboard.KEY_LSHIFT));

        if (KeyboardControl.isKeyDown(Keyboard.KEY_W))
        {
            move(Camera.FORWARD);
        }
        if (KeyboardControl.isKeyDown(Keyboard.KEY_S))
        {
            move(Camera.BACKWARD);
        }

        if (KeyboardControl.isKeyDown(Keyboard.KEY_D))
        {
            move(Camera.RIGHT);
        }
        if (KeyboardControl.isKeyDown(Keyboard.KEY_A))
        {
            move(Camera.LEFT);
        }
    }
}
