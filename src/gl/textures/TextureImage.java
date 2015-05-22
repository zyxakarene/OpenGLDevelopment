package gl.textures;

import gl.glUtils.BufferControls;
import gl.glUtils.GLUtils;
import gl.shaders.ShaderLoader;
import gl.shaders.ShaderType;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.BufferedImageUtil;
import org.newdawn.slick.util.ResourceLoader;

class TextureImage
{

    private static Texture currentTexture;
    private Texture texture;

    TextureImage(String name, BufferedImage image) throws IOException
    {
        texture = BufferedImageUtil.getTexture(name, image, GL11.GL_NEAREST);
    }

    TextureImage(String name, String format) throws IOException
    {
        String filename = String.format("textures/%s.%s", name, format);
        texture = TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream(filename));
    }

    void bind()
    {
        if (currentTexture != texture)
        {
            ShaderLoader.activateShader(ShaderType.TRANSFORM);
            BufferControls.activeTexture(0);
            texture.bind();
            currentTexture = texture;
            GLUtils.errorCheck();
        }
    }
}