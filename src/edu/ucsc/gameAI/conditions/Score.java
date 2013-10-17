package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class Score implements ICondition {
    
    private int _min,_max;
    
    public Score(int min, int max)
    {
        _min = min;
        _max = max;
    }
    
    public boolean test(Game game) 
    {
        int score =game.getScore();
        return (score >= _min) && (score <= _max);
    }
}
