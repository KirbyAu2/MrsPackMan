package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class CurrentLevelTime implements ICondition {

    private int _min,_max;
    
    public CurrentLevelTime(int min, int max)
    {
        _min = min;
        _max = max;
    }
    
    public boolean test(Game game) 
    {
        int currentLevel = game.getCurrentLevelTime();
        return (currentLevel >= _min) && (currentLevel <= _max);
    }
}
