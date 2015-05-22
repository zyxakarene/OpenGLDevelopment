package game.world.map;

import game.camera.Camera;
import game.control.ElapsedTime;
import game.control.clicker.ClickRegistrator;
import game.world.Hud;
import game.world.basic.GameEntity;
import gl.lighting.Shadow;
import org.lwjgl.util.vector.Vector3f;
import utils.constants.GameConstants;
import utils.constants.TextureConstants;
import utils.constants.TileTypes;
import utils.interfaces.IDrawable;
import utils.interfaces.IUpdateable;

public class World implements IDrawable, IUpdateable
{

    private Tile[][] tiles;

    public World()
    {
        Camera.create(new Vector3f(-5f, -7f, -6f), new Vector3f(-50, 0, 223), GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
        
        tiles = new Tile[20][10];
        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles[i].length; j++)
            {
                Tile tile = new Tile((int) (TileTypes.CLIFF_INNER_CORNER * Math.random()));
                tile.setX(Tile.WIDTH * i);
                tile.setY(Tile.WIDTH * j);
                tiles[i][j] = tile;
            }
        }
    }

    @Override
    public void draw()
    {
        Camera.clearView();
        Camera.look();

        drawShadow();
        drawScene();

        Hud.draw();
    }

    private void drawShadow()
    {
        Shadow.begin();
        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles[i].length; j++)
            {
                tiles[i][j].drawShadow();
            }
        }
        Shadow.end();
    }

    private void drawScene()
    {
        ClickRegistrator.draw();
//        for (int i = 0; i < tiles.length; i++)
//        {
//            for (int j = 0; j < tiles[i].length; j++)
//            {
//                tiles[i][j].drawClick();
//            }
//        }
    }

    @Override
    public void update()
    {
        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles[i].length; j++)
            {
                tiles[i][j].changeYaw(0.1f * ElapsedTime.get());
            }
        }
    }
}
