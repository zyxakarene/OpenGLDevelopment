/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.world;

import game.control.clicker.ClickBuffer;
import gl.glUtils.BufferControls;
import gl.lighting.Shadow;
import gl.shaders.HudShader;
import gl.shaders.ShaderLoader;
import gl.shaders.ShaderType;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Rene
 */
public class Hud
{

    private static final int vao, vbo, ebo, elementSize, vao2;

    static
    {
        vao = glGenVertexArrays();
        vao2 = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();

        float vertices[] =
        {
            //  Position    Texcoords
            -1f, -0.5f, 0.0f, 1.0f, // Top-left
            -0.5f, -0.5f, 1.0f, 1.0f, // Top-right
            -0.5f, -1f, 1.0f, 0.0f, // Bottom-right
            -1f, -1f, 0.0f, 0.0f  // Bottom-left
        };


        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);


        // Create an element array
        ebo = glGenBuffers();
        int elements[] =
        {
            0, 1, 2,
            2, 3, 0
        };
        elementSize = elements.length;

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elements.length);
        elementBuffer.put(elements);
        elementBuffer.flip();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);


        
        // Specify the layout of the vertex data
        int posAttrib = glGetAttribLocation(HudShader.shader().getProgram(), "position");
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 2, GL_FLOAT, false, (Float.SIZE / 8) * 4, 0);

//        int colAttrib = glGetAttribLocation(Shaders.hudProgam, "color");
//        glEnableVertexAttribArray(colAttrib);
//        glVertexAttribPointer(colAttrib, 3, GL_FLOAT, false, (Float.SIZE / 8) * 7, (Float.SIZE / 8) * 2);

        int texAttrib = glGetAttribLocation(HudShader.shader().getProgram(), "texcoord");
        glEnableVertexAttribArray(texAttrib);
        glVertexAttribPointer(texAttrib, 2, GL_FLOAT, false, (Float.SIZE / 8) * 4, (Float.SIZE / 8) * 2);
        
        glBindVertexArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public static void draw()
    {
        ShaderLoader.activateShader(ShaderType.HUD);
        ClickBuffer.bindTexture();
        glDisable(GL_CULL_FACE);


        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);



        glDrawElements(GL_TRIANGLES, elementSize, GL_UNSIGNED_INT, 0);
        
        glEnable(GL_CULL_FACE);
    }
}
