package game.world.map;

import game.ai.AttackManager;
import game.ai.MapSolver;
import game.ai.enemies.EnemyManager;
import game.ai.towers.TowerManager;
import game.camera.Camera;
import game.control.KeyboardControl;
import game.control.clicker.ClickRegistrator;
import game.physics.PhysObject;
import game.physics.PhysicsManager;
import game.sound.SoundManager;
import game.world.Hud;
import game.world.io.MapLoader;
import game.world.io.MapSaver;
import gl.lighting.Shadow;
import gl.shaders.ShaderLoader;
import gl.shaders.ShaderType;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import utils.constants.GameConstants;
import utils.constants.TileTypes;
import utils.interfaces.IDrawable;
import utils.interfaces.IUpdateable;

public class World implements IDrawable, IUpdateable
{

    private ArrayList<Doodad> doodads;
    private Tile[][] tiles;
    private Skybox skybox;
    private ArrayList<PhysObject> phys = new ArrayList<>();

    public World()
    {
        Camera.create(new Vector3f(-5f, -7f, -6f), new Vector3f(-50, 0, 223), GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);

        MapLoader loader = new MapLoader();
        tiles = loader.loadMap("MapFile.map");
        skybox = new Skybox();

        doodads = new ArrayList<>();

        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles[i].length; j++)
            {
                if (TileTypes.isLane(tiles[i][j].getTileType()) == false)
                {
                    doodads.add(new Doodad(tiles[i][j], tiles[i][j].getTileType()));
                }
            }
        }

        KeyboardControl.listenForHolding(Keyboard.KEY_LCONTROL);
    }

    @Override
    public void draw()
    {
        Camera.clearView();
        Camera.look();

        skybox.draw();

        drawShadow();
        drawScene();
        PhysicsManager.instance.draw();

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

        for (PhysObject physObject : phys)
        {
            physObject.drawShadow();
        }

        EnemyManager.instance.drawShadow();
        TowerManager.instance.drawShadow();

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


        EnemyManager.instance.draw();
        TowerManager.instance.draw();

        for (int i = 0; i < doodads.size(); i++)
        {
            doodads.get(i).draw();
        }
        
        ShaderLoader.activateShader(ShaderType.PHYS);
        for (PhysObject physObject : phys)
        {
            physObject.draw();
        }
    }

    public void update()
    {
        if (KeyboardControl.wasKeyPressed(Keyboard.KEY_SPACE))
        {
            PhysObject physObject = new PhysObject();
            physObject.setPos(Camera.getX(), Camera.getY(), Camera.getZ());
            phys.add(physObject);

            PhysicsManager.instance.addEntity(physObject);
        }

        if (KeyboardControl.wasKeyPressed(Keyboard.KEY_RETURN))
        {
            EnemyManager.instance.addEnemy();
        }

        if (KeyboardControl.isKeyDown(Keyboard.KEY_LCONTROL) && KeyboardControl.wasKeyPressed(Keyboard.KEY_S))
        {
            new MapSaver(tiles).saveMapTo("MapFile.map");
        }

        ClickRegistrator.checkClick();
    }

    @Override
    public void update(int elapsedTime)
    {
        PhysicsManager.instance.update(elapsedTime / 1000f);
        EnemyManager.instance.update(elapsedTime);
        TowerManager.instance.update(elapsedTime);

        for (PhysObject physObject : phys)
        {
            physObject.update(0);
        }

        AttackManager.instance.update(elapsedTime);
        SoundManager.update(elapsedTime);
    }
}
