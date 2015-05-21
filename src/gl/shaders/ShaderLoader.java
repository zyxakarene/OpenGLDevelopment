package gl.shaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class ShaderLoader
{

    private static int currentShaderProgram;

    public static void loadShaders() throws FileNotFoundException
    {
        HudShader.load();
        SimpleDepthShader.load();
        TransformShader.load();
    }

    public static void activateShader(Class shaderClass)
    {
        int classProgram = getShaderFrom(shaderClass);

        if (classProgram != currentShaderProgram)
        {
            activateProgramFrom(shaderClass);
            currentShaderProgram = classProgram;
        }
    }

    public static int getCurrentShader()
    {
        return currentShaderProgram;
    }

    static String loadFile(String name) throws FileNotFoundException
    {
        StringBuilder builder = new StringBuilder();

        File file = new File("src/gl/shaders/source/" + name);

        Scanner scan = new Scanner(file);

        while (scan.hasNextLine())
        {
            builder.append(scan.nextLine());
            builder.append("\n");
        }

        return builder.toString();
    }

    private static int getShaderFrom(Class shaderClass)
    {
        try
        {
            Method method = shaderClass.getMethod("getProgram");
            return (int) method.invoke(null);
        }
        catch (Exception ex)
        {
            String msg = "Could not retrieve shader from class '%s'";
            throw new RuntimeException(String.format(msg, shaderClass), ex);
        }
    }

    private static void activateProgramFrom(Class shaderClass)
    {
        try
        {
            Method method = shaderClass.getMethod("activate");
            method.invoke(null);
        }
        catch (Exception ex)
        {
            String msg = "Could not activate ShaderProgram from class '%s'";
            throw new RuntimeException(String.format(msg, shaderClass), ex);
        }
    }
}
