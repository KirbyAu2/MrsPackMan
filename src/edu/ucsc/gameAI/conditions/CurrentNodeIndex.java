package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class CurrentNodeIndex implements ICondition {
    
    private Game _game;
    private GHOST _ghost;
    private int _min,_max;

    public CurrentNodeIndex(Game game, GHOST ghost, int min, int max)
    {
        _game = game;
        _ghost = ghost;
        _min = min;
        _max = max;
    }
    
    
    public boolean test() 
    {
        int currentNodeIndex = _game.getGhostCurrentNodeIndex(_ghost);
        return (currentNodeIndex >= _min) && (currentNodeIndex <= _max);
    }
}
