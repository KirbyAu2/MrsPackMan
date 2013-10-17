package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class LairTime implements ICondition {
    
    private GHOST _ghost;
    private int _min,_max;
    
    public LairTime(GHOST ghost, int min, int max)
    {
        _ghost = ghost;
        _min = min;
        _max = max;
    }
    
    public boolean test(Game game) 
    {
        int lairTime = game.getGhostLairTime(_ghost);
      
        return (lairTime >= _min) && (lairTime <= _max);
    }
}
