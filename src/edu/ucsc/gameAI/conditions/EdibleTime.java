package src.edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class EdibleTime implements ICondition {
    
    private Game _game;
    private GHOST _ghost;
    private int _min,_max;
    
    public EdibleTime(Game game, GHOST ghost, int min, int max)
    {
        _game = game;
        _ghost = ghost;
        _min = min;
        _max = max;
    }
    
    public boolean test() 
    {
        int edibleTime = _game.getGhostEdibleTime(_ghost);
        return (edibleTime >= _min) && (edibleTime <= _max);
    }
}
