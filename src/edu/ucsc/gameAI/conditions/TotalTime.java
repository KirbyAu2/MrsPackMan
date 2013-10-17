package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class TotalTime implements ICondition {
    
    private int _min,_max;
    
    public TotalTime(int min, int max)
    {
        _min = min;
        _max = max;
    }
    
    public boolean test(Game game) 
    {
        int totalTime = game.getTotalTime();
        return (totalTime >= _min) && (totalTime <= _max);
    }
}
