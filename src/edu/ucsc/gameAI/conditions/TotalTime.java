package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class TotalTime implements ICondition {
    
    private Game _game;
    private int _min,_max;
    
    public TotalTime(Game game, int min, int max)
    {
        _game = game;
        _min = min;
        _max = max;
    }
    
    public boolean test() 
    {
        int totalTime = _game.getTotalTime();
        return (totalTime >= _min) && (totalTime <= _max);
    }
}
