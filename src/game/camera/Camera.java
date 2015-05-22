package game.camera;

import game.control.ElapsedTime;
import gl.shaders.SharedShaderObjects;
import gl.shaders.ShaderLoader;
import gl.shaders.ShaderType;
import gl.shaders.TransformShader;
//import gl.shaders.SimpleDepthShader;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Matrix4f;

import static org.lwjgl.opengl.GL11.*;
import utils.FloatMath;

public class Camera
{

    private static final Matrix4f SHARED_VIEW_TRANSFORM = SharedShaderObjects.SHARED_VIEW_TRANSFORM;
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

    public static void create(Vector3f pos, int height, int width)
    {
        create(pos, new Vector3f(), height, width);
    }

    public static Vector3f getDir()
    {
        float dX = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch + 90))) * 10.5f;
        float dY = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch + 90))) * 10.5f;
        float dZ = (float) (Math.cos(Math.toRadians(pitch))) * 10.5f;

        Vector3f f = new Vector3f(-(x - dX), -(y - dY), -(z + dZ));
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

        ShaderLoader.activateShader(ShaderType.TRANSFORM);
        Matrix4f projectionView = perspective(70f, screenWidth / screenHeight, 0.01f, 2f);
        TransformShader.shader().setupProjection(projectionView);
    }

    public static void move(int direction)
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

    public static void rotate(Vector3f amount)
    {
        float multiplier = ElapsedTime.get() * 0.008f;
        amount.scale(multiplier);
        
        pitch = pitch + amount.x;
        roll = roll + amount.y;
        yaw = yaw + amount.z;

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
        ShaderLoader.activateShader(ShaderType.TRANSFORM);
        updateView();
        TransformShader.shader().updateViewUniform();
        TransformShader.shader().setViewPos(-x, -y, -z);

        if (!isPerspective)
        {
            int wheel = Mouse.getDWheel();
            if (wheel > 0)
            {
                orthoZoom *= 0.9f;
            }
            else if (wheel < 0)
            {
                orthoZoom *= 1.1f;
            }
        }

        if (Mouse.isButtonDown(0))
        {
            Matrix4f projectionView = perspective(70f, screenWidth / screenHeight, 0.01f, 2f);
            TransformShader.shader().setupProjection(projectionView);
        }
        else if (Mouse.isButtonDown(1) || !isPerspective)
        {
            Matrix4f projectionView = orthographic(screenWidth * orthoZoom, screenHeight * orthoZoom, 0.01f, 100f);
            TransformShader.shader().setupProjection(projectionView);
        }
    }

    private static Matrix4f perspective(float fov, float aspect, float near, float far)
    {
        isPerspective = true;

        Matrix4f m = new Matrix4f();

        float angle = (float) ((fov / 180.0f) * Math.PI);
        float f = (float) (1.0f / Math.tan(angle * 0.5f));

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
//        OrthoMatrix.m01 = 0.0f;
//        OrthoMatrix.m02 = 0.0f;
//        OrthoMatrix.m03 = 0.0f;

//        OrthoMatrix.m10 = 0.0f;
        OrthoMatrix.m11 = 2.0f / (top - bottom);
//        OrthoMatrix.m12 = 0.0f;
//        OrthoMatrix.m13 = 0.0f;

//        OrthoMatrix.m20 = 0.0f;
//        OrthoMatrix.m21 = 0.0f;
        OrthoMatrix.m22 = -2.0f / (far - near);
//        OrthoMatrix.m23 = 0.0f;

        OrthoMatrix.m30 = -(right + left) / (right - left);
        OrthoMatrix.m31 = -(top + bottom) / (top - bottom);
        OrthoMatrix.m32 = -(far + near) / (far - near);
        OrthoMatrix.m33 = 1.0f;

        return OrthoMatrix;
    }

    private static void updateView()
    {
        SHARED_VIEW_TRANSFORM.setIdentity();
        SHARED_VIEW_TRANSFORM.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0));
        SHARED_VIEW_TRANSFORM.rotate((float) Math.toRadians(roll), new Vector3f(0, 1, 0));
        SHARED_VIEW_TRANSFORM.rotate((float) Math.toRadians(yaw), new Vector3f(0, 0, 1));
        SHARED_VIEW_TRANSFORM.translate(new Vector3f(x, y, z));

        float dX = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch + 90))) * 0.1f;
        float dY = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch + 90))) * 0.1f;
        float dZ = (float) (Math.cos(Math.toRadians(pitch))) * 0.1f;
        SHARED_VIEW_TRANSFORM.translate(new Vector3f(-dX * 10, -dY * 10, dZ * 10));
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

    public static void clearView()
    {
        glClearColor(0.4f, 0.6f, 0.9f, 0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void setIsFast(boolean keyDown)
    {
        moveSpeed = keyDown ? 1 : 0.2f;
    }
}
