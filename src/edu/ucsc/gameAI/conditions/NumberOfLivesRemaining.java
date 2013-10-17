package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class NumberOfLivesRemaining implements ICondition {
    
    private int _min,_max;
    
    public NumberOfLivesRemaining(int min, int max)
    {
        _min = min;
        _max = max;
    }
    
    public boolean test(Game game) 
    {
        int livesRemaining = game.getPacmanNumberOfLivesRemaining();
        
        return (livesRemaining >= _min) && (livesRemaining <= _max);
    }
}
