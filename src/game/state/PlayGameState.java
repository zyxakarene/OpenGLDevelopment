package game.state;

import game.camera.Camera;
import game.world.map.World;
import utils.interfaces.IGameStateElement;

public class PlayGameState implements IGameStateElement
{

    private World world;

    public PlayGameState()
    {
        world = new World();
    }

    @Override
    public void update()
    {
        world.update();
    }

    @Override
    public void draw()
    {
        world.draw();
    }

    @Override
    public void update(int elapsedTime)
    {
        world.update(elapsedTime);
        Camera.update(elapsedTime);

    }
}
