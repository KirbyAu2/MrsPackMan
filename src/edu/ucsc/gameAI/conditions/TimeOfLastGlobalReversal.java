package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class TimeOfLastGlobalReversal implements ICondition {
    
    private int _min,_max;
    
    public TimeOfLastGlobalReversal(int min, int max)
    {
        _min = min;
        _max = max;
    }
    
    public boolean test(Game game) 
    {
        int timeOfLastGlobalReversal = game.getTimeOfLastGlobalReversal();
        return (timeOfLastGlobalReversal >= _min) && (timeOfLastGlobalReversal <= _max);
    }
}
