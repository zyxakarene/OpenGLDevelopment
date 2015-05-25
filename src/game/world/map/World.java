package game.world.map;

import game.ai.Enemy;
import game.camera.Camera;
import game.control.KeyboardControl;
import game.control.clicker.ClickRegistrator;
import game.world.Hud;
import game.world.io.MapLoader;
import game.world.io.MapSaver;
import gl.lighting.Shadow;
import gl.shaders.ShaderLoader;
import gl.shaders.ShaderType;
import java.util.ArrayList;
import main.devGui.EditGui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import utils.constants.GameConstants;
import utils.interfaces.IDrawable;
import utils.interfaces.IUpdateable;

public class World implements IDrawable, IUpdateable
{

    private Tile[][] tiles;
    private ArrayList<Enemy> enemies;
    private Skybox skybox;

    public World()
    {
        Camera.create(new Vector3f(-5f, -7f, -6f), new Vector3f(-50, 0, 223), GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);

        tiles = new MapLoader().loadMap("MapFile.map");
        enemies = new ArrayList<>();
        skybox = new Skybox();

        new EditGui().setVisible(true);
    }

    @Override
    public void draw()
    {
        Camera.clearView();
        Camera.look();

        skybox.draw();
        
        drawShadow();
        drawScene();

        Hud.draw();
    }

    private void drawShadow()
    {
        ShaderLoader.activateShader(ShaderType.DEBTH);
        
        Shadow.begin();
        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles[i].length; j++)
            {
                tiles[i][j].drawShadow();
            }
        }

        for (int i = 0; i < enemies.size(); i++)
        {
            enemies.get(i).drawShadow();
        }
        Shadow.end();
    }

    private void drawScene()
    {
        ShaderLoader.activateShader(ShaderType.TRANSFORM);
        
        Shadow.bindTexture();
        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles[i].length; j++)
            {
                tiles[i][j].draw();
            }
        }

        for (int i = 0; i < enemies.size(); i++)
        {
            enemies.get(i).draw();
        }
    }

    @Override
    public void update()
    {
        Camera.update();

        for (int i = 0; i < enemies.size(); i++)
        {
            enemies.get(i).update();
        }

        ClickRegistrator.checkClick();

        if (KeyboardControl.wasKeyPressed(Keyboard.KEY_RETURN))
        {
            enemies.add(new Enemy());
        }
        
        if (KeyboardControl.isKeyDown(Keyboard.KEY_LCONTROL) && KeyboardControl.wasKeyPressed(Keyboard.KEY_S))
        {
            new MapSaver(tiles).saveMapTo("MapFile.map");
        }
    }
}
