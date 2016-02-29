package game.physics.debug;

import gl.shaders.LineShader;
import gl.shaders.ShaderLoader;
import gl.shaders.ShaderType;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
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


public class LineModel
{
    private static final int vao, vbo, ebo, elementSize;

    static
    {
        ShaderLoader.activateShader(ShaderType.LINE);
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();

        float[] vertecies = new float[]
        {
            0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            10f, 10f, 10f, 1.0f, 0.0f, 0.0f
        };

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertecies.length);
        buffer.put(vertecies);
        buffer.flip();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);


        // Create an element array
        ebo = glGenBuffers();
        int elements[] = new int[]
        {
            0, 1
        };
        elementSize = elements.length;

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elements.length);
        elementBuffer.put(elements);
        elementBuffer.flip();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);


        glBindVertexArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public static void setPos(float xA, float yA, float zA, float xB, float yB, float zB)
    {
        ShaderLoader.activateShader(ShaderType.LINE);
        float[] vertecies = new float[]
        {
            xA, yA, zA,
            xB, yB, zB,
        };

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertecies.length);
        buffer.put(vertecies);
        buffer.flip();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    public static void draw()
    {
        ShaderLoader.activateShader(ShaderType.LINE);
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);


        // Specify the layout of the vertex data
        int posAttrib = glGetAttribLocation(LineShader.shader().getProgram(), "position");
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, (Float.SIZE / 8) * 3, 0);


//        FloatBuffer modelBuffer = BufferUtils.createFloatBuffer(16);
//        Matrix4f matrix = new Matrix4f();
//        matrix.store(modelBuffer);
//        modelBuffer.flip();
//        glUniformMatrix4(Shaders.uniTrans, false, modelBuffer);

        glDrawElements(GL_LINES, elementSize, GL_UNSIGNED_INT, 0);
    }
}
