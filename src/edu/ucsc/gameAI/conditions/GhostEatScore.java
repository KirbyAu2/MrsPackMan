package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class GhostEatScore implements ICondition {
    
    private int _min,_max;
    
    public GhostEatScore(int min, int max)
    {
        _min = min;
        _max = max;
    }
    
    public boolean test(Game game) 
    {
        int edibleScore=game.getGhostCurrentEdibleScore();
        return (edibleScore >= _min) && (edibleScore <=_max);
    }
}
