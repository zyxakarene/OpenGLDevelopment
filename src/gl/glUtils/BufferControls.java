package gl.glUtils;

import gl.textures.TextureManager;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import org.lwjgl.opengl.GL30;
import static org.lwjgl.opengl.GL30.*;

public class BufferControls
{

    private static int lastActiveTextureSlot = -1;
    
    /**
     * Generates and returns a new Texture id.
     * <p/>
     * @return The new Texture.
     */
    public static int generateTexture()
    {
        return glGenTextures();
    }

    /**
     * Binds the Texture id.
     * <p/>
     */
    public static void bindTexture(int texture)
    {
        TextureManager.unbind();
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public static void createTexture(int width, int height)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0, GL_DEPTH_COMPONENT, GL_INT, (FloatBuffer) null);
    }

    public static void setupTexture()
    {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        float borderColor[] =
        {
            0.0f, 0.0f, 0.0f, 0.0f
        };

        FloatBuffer buffer = BufferUtils.createFloatBuffer(borderColor.length);
        buffer.put(borderColor);
        buffer.flip();
        glTexParameter(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, buffer);
    }

    public static void frameBufferToColor(int frameBuffer, int renderBuffer, int texture)
    {
        glTexParameterf(GL11.GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);                                         // make it linear filterd
        glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL_RGBA8, 128, 128, 0, GL_RGBA, GL_UNSIGNED_BYTE, (FloatBuffer) null);       // Create the texture data
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, texture, 0);           // attach it to the framebuffer


        // initialize depth renderbuffer
        glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderBuffer);                                                 // bind the depth renderbuffer
        glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL_DEPTH_COMPONENT24, 128, 128);                                   // get the data space for it
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, renderBuffer);     // bind it to the renderbuffer

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }
    
    public static void frameBufferToDepth(int buffer, int texture)
    {
        glBindFramebuffer(GL_FRAMEBUFFER, buffer);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, texture, 0);
        glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    /**
     * Generates and returns a new EBO.
     * The VAO is an object that the order of verticies to be drawn.
     * Eg, it can draw 2 triangles from a set of 4 verticies.
     * <p/>
     * @return The new EBO.
     */
    public static int generateEBO()
    {
        return glGenBuffers();
    }

    /**
     * Binds the EBO to set it to the active one.
     * <p/>
     * @param ebo The EBO to bind.
     */
    public static void bindEBO(int ebo)
    {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
    }

    public static void fillEBO_Static(int[] array)
    {
        IntBuffer buffer = toBuffer(array);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    public static void fillEBO_Dynamic(int[] array)
    {
        IntBuffer buffer = toBuffer(array);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
    }

    public static void fillEBO_Stream(int[] array)
    {
        IntBuffer buffer = toBuffer(array);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STREAM_DRAW);
    }

    /**
     * Generates a new VAO id and returns it.
     * The VBO is an object that contains all information
     * about the raw vertex data and how to render it.<br>
     * See: {@link bindVAO()}.
     * <p/>
     * @return The new VAO.
     */
    public static int generateVAO()
    {
        return glGenVertexArrays();
    }

    /**
     * Binds a VAO to the active one.
     * Only needs to be done if you need to render an object
     * that is setup in a different way than the previous one.<br>
     * See: {@link generateVAO()}.
     * <p/>
     * @param bufferId The VAO to bind.
     */
    public static void bindVAO(int vao)
    {
        glBindVertexArray(vao);
    }

    /**
     * Generates and returns a new VBO.
     * The VAO is an object that contains the raw vertex data.
     * <p/>
     * @return The new VBO.
     */
    public static int generateVBO()
    {
        return glGenBuffers();
    }

    public static int generateRenderBuffer()
    {
        return glGenRenderbuffers();
    }

    public static int generateFrameBuffer()
    {
        return glGenFramebuffers();
    }

    public static void bindVBO(int vbo)
    {
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
    }

    public static void bindFrameBuffer(int frameBuffer)
    {
        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
    }

    public static void fillVBO_Static(float[] array)
    {
        FloatBuffer buffer = toBuffer(array);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    public static void fillVBO_Dynamic(float[] array)
    {
        FloatBuffer buffer = toBuffer(array);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
    }

    public static void fillVBO_Stream(float[] array)
    {
        FloatBuffer buffer = toBuffer(array);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STREAM_DRAW);
    }

    private static FloatBuffer toBuffer(float[] array)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
        buffer.put(array);
        buffer.flip();

        return buffer;
    }

    private static IntBuffer toBuffer(int[] array)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(array.length);
        buffer.put(array);
        buffer.flip();

        return buffer;
    }

    public static void activeTexture(int textureSlot)
    {
        if (lastActiveTextureSlot == textureSlot)
        {
            return;
        }
        
        if (textureSlot == 0)
        {
            glActiveTexture(GL_TEXTURE0);
        }
        else if (textureSlot == 1)
        {
            glActiveTexture(GL_TEXTURE1);
        }
        else if (textureSlot == 2)
        {
            glActiveTexture(GL_TEXTURE2);
        }
        else if (textureSlot == 3)
        {
            glActiveTexture(GL_TEXTURE3);
        }
    }
}
