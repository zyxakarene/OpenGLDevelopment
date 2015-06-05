package gl.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import org.newdawn.slick.opengl.TextureImpl;
import utils.constants.TextureConstants;

public class TextureManager
{

    private static HashMap<String, TextureImage> map = new HashMap<>();

    public static void initTextures() throws IOException
    {
//        add("Box001", "jpg");
//        add("floor", "jpg");
//        add("Plane001", "jpg");
//        add("sample", "png");
//        add("WoodPlank", "png");
//        add("Tiles", "png");
        add(TextureConstants.TILES, "png");
        add(TextureConstants.ENEMY_SET, "png");
        add(TextureConstants.SKYBOX, "png");
        add(TextureConstants.TOWER_BASE, "png");
    }

    public static void bind(String name)
    {
        map.get(name).bind();
    }

    private static void add(String name, BufferedImage img) throws IOException
    {
        TextureImage image = new TextureImage(name, img);
        map.put(name, image);
    }

    private static void add(String name, String format) throws IOException
    {
        TextureImage image = new TextureImage(name, format);
        map.put(name, image);
    }

    public static void unbind()
    {
        TextureImage.currentTexture = null;
        TextureImpl.unbind();
    }
}
