package game.state;

import utils.interfaces.IDrawable;
import utils.interfaces.IGameStateElement;
import utils.interfaces.IUpdateable;

public class GameStateManager implements IDrawable, IUpdateable
{

    public static final GameStateManager instance = new GameStateManager();
    private static final int STATE_MENU = 0;
    private static final int STATE_PLAY = 1;
    private int currentState;
    private IGameStateElement[] gameStates;

    private GameStateManager()
    {
        gameStates = new IGameStateElement[2];
    }

    public void setMenuState()
    {
        if (gameStates[STATE_MENU] == null)
        {
            gameStates[STATE_MENU] = new MenuGameState();
        }

        currentState = STATE_MENU;
    }

    public void setPlayState()
    {
        if (gameStates[STATE_PLAY] == null)
        {
            gameStates[STATE_PLAY] = new PlayGameState();
        }

        currentState = STATE_PLAY;
    }

    @Override
    public void draw()
    {
        gameStates[currentState].draw();
    }

    @Override
    public void update(int elapsedTime)
    {
        gameStates[currentState].update(elapsedTime);
        gameStates[currentState].update();
    }
}
