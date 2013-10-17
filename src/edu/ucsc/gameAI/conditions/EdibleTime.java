package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class EdibleTime implements ICondition {
    
    private GHOST _ghost;
    private int _min,_max;
    
    public EdibleTime(GHOST ghost, int min, int max)
    {
        _ghost = ghost;
        _min = min;
        _max = max;
    }
    
    public boolean test(Game game) 
    {
        int edibleTime = game.getGhostEdibleTime(_ghost);
        return (edibleTime >= _min) && (edibleTime <= _max);
    }
}
