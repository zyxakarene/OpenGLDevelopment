package gl.lighting;

import gl.glUtils.BufferControls;
import gl.glUtils.GLUtils;
import gl.shaders.ShaderLoader;
import gl.shaders.SharedShaderObjects;
import gl.shaders.SimpleDepthShader;
import gl.shaders.TransformShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Shadow
{

    public static float pitchC = -69.5f;
    public static float rollC = 0f;
    public static float yawC = 224f;
//    public static float pitchC = -60.5f;
//    public static float rollC = 0f;
//    public static float yawC = 2223f;
    
    public static float moveMultiplier = 1;
    
    private static int buffer;
    private static int texture;
    private static boolean shouldRemap;

    public static void setup(int width, int height)
    {
        ShaderLoader.activateShader(SimpleDepthShader.class);
        Matrix4f projectionView = orthographic(width, height, 0.01f, 100f);
        SimpleDepthShader.setupProjection(projectionView);

        ShaderLoader.activateShader(TransformShader.class);
        TransformShader.setupLightProjection(projectionView);
        setupView();

        ShaderLoader.activateShader(SimpleDepthShader.class);

        buffer = BufferControls.generateFrameBuffer();
        BufferControls.bindFrameBuffer(buffer);

        texture = BufferControls.generateTexture();

        BufferControls.activeTexture(1);
        BufferControls.bindTexture(texture);
        BufferControls.createTexture(width, height);
        BufferControls.setupTexture();
        BufferControls.somethingWithShadows(buffer, texture);
    }

    public static void bindTexture()
    {
        BufferControls.activeTexture(1);
        BufferControls.bindTexture(texture);
    }

    public static void begin()
    {
        if (shouldRemap)
        {
            shouldRemap = false;
            setupView();
        }
        
        GLUtils.cullFront();
        BufferControls.bindFrameBuffer(buffer);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
    }

    public static void end()
    {
        GLUtils.cullBack();
        BufferControls.bindFrameBuffer(0);
    }

    private static void setupView()
    {
        Matrix4f viewMatrix = SharedShaderObjects.SHARED_VIEW_TRANSFORM;
        viewMatrix.setIdentity();
        viewMatrix.rotate((float) Math.toRadians(pitchC), new Vector3f(1, 0, 0));
        viewMatrix.rotate((float) Math.toRadians(rollC), new Vector3f(0, 1, 0));
        viewMatrix.rotate((float) Math.toRadians(yawC), new Vector3f(0, 0, 1));
//        viewMatrix.translate(new Vector3f(-25.4f, -27.4f, -19.1f));
        viewMatrix.translate(new Vector3f(-61, -64, -34));
//        viewMatrix.translate(new Vector3f(0f, -1f, -4.5f));

//        float dX = -0.06506672f;
//        float dY = -0.06737856f;
//        float dZ = 0.03502074f;
        float dX = (float) (Math.sin(Math.toRadians(yawC)) * Math.cos(Math.toRadians(pitchC + 90))) * 0.1f;
        float dY = (float) (Math.cos(Math.toRadians(yawC)) * Math.cos(Math.toRadians(pitchC + 90))) * 0.1f;
        float dZ = (float) (Math.cos(Math.toRadians(pitchC))) * 0.1f;
        viewMatrix.translate(new Vector3f(-dX * moveMultiplier, -dY * moveMultiplier, dZ * moveMultiplier));

        ShaderLoader.activateShader(SimpleDepthShader.class);
        SimpleDepthShader.updateViewUniform();

        ShaderLoader.activateShader(TransformShader.class);
        TransformShader.updateLightViewUniform();
//        TransformShader.setLightDirection(pitchC, yawC, rollC);
    }

    private static Matrix4f orthographic(float width, float height, float near, float far)
    {
        float scale = 50f;
        float left = -width / scale;
        float right = width / scale;
        float top = height / scale;
        float bottom = -height / scale;

        Matrix4f OrthoMatrix = new Matrix4f();

        OrthoMatrix.m00 = 2.0f / (right - left);
        OrthoMatrix.m01 = 0.0f;
        OrthoMatrix.m02 = 0.0f;
        OrthoMatrix.m03 = 0.0f;

        OrthoMatrix.m10 = 0.0f;
        OrthoMatrix.m11 = 2.0f / (top - bottom);
        OrthoMatrix.m12 = 0.0f;
        OrthoMatrix.m13 = 0.0f;

        OrthoMatrix.m20 = 0.0f;
        OrthoMatrix.m21 = 0.0f;
        OrthoMatrix.m22 = -2.0f / (far - near);
        OrthoMatrix.m23 = 0.0f;

        OrthoMatrix.m30 = -(right + left) / (right - left);
        OrthoMatrix.m31 = -(top + bottom) / (top - bottom);
        OrthoMatrix.m32 = -(far + near) / (far - near);
        OrthoMatrix.m33 = 1.0f;

        return OrthoMatrix;
    }

    public static void remap()
    {
        shouldRemap = true;
    }
}
