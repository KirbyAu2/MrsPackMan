package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class GhostEatMultiplier implements ICondition {
    
    private Game _game;
    private int _min,_max;
    
    public GhostEatMultiplier(Game game, int min, int max)
    {
        _game = game;
        _min = min;
        _max = max;
    }
    
    public boolean test() 
    {
        int edibleScore=_game.getGhostCurrentEdibleScore();
        return (edibleScore >= _min) && (edibleScore <=_max);
    }
}
