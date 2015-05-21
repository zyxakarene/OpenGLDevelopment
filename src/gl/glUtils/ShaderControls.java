package gl.glUtils;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class ShaderControls
{

    public static void setUniformMatrix(int uniform, FloatBuffer buffer)
    {
        glUniformMatrix4(uniform, false, buffer);
    }
    
    public static void setUniform1I(int uniform, int location)
    {
        glUniform1i(uniform, location);
    }
    
    public static void setUniform3F(int uniform, float a, float b, float c)
    {
        glUniform3f(uniform, a, b, c);
    }
    
    public static int createAttribute(int program, String name)
    {
        return glGetAttribLocation(program, name);
    }
    
    public static int createUniform(int program, String name)
    {
        return glGetUniformLocation(program, name);
    }

    public static void assignAtribute(int attribute, int components, int stride, int offset)
    {
        glEnableVertexAttribArray(attribute);
        glVertexAttribPointer(attribute, components, GL_FLOAT, false, (Float.SIZE / 8) * stride, (Float.SIZE / 8) * offset);
    }

    public static int generateVertexShader()
    {
        return glCreateShader(GL_VERTEX_SHADER);
    }

    public static int generateFragmentShader()
    {
        return glCreateShader(GL_FRAGMENT_SHADER);
    }

    public static void createShaderFrom(int shaderId, String shaderSource)
    {
        glShaderSource(shaderId, shaderSource);
        glCompileShader(shaderId);

        checkCompileStatus(shaderId);
    }

    public static int createShaderProgram(int... shaders)
    {
        int program = glCreateProgram();

        for (int i = 0; i < shaders.length; i++)
        {
            int shaderId = shaders[i];
            glAttachShader(program, shaderId);
        }
        System.out.println("program " + program + " created from " + Arrays.toString(shaders));
        return program;
    }

    public static void bindFragmentLocation(int shaderProgam, int colorNumber, String name)
    {
        glBindFragDataLocation(shaderProgam, colorNumber, name);
    }

    private static void checkCompileStatus(int shaderId)
    {
        if ((glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_TRUE))
        {
            System.out.println(String.format("shader %s compiled.", shaderId));
        }
        else
        {
            String error = glGetShaderInfoLog(shaderId, 512);
            String errorMsg = String.format("A shader was not compiled correctly:\n%s", error);
            Logger.getLogger("Shader Logger").log(Level.SEVERE, errorMsg);
        }
    }
    
    public static void link(int program)
    {
        glLinkProgram(program);
    }
    
    public static void use(int program)
    {
        glUseProgram(program);
    }

    public static void enableTextureAttrib(int textureAttribute)
    {
        glEnableVertexAttribArray(textureAttribute);
    }
}
