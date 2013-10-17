package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class IsEdible implements ICondition {
        
    private GHOST _ghost;
    
    public IsEdible(GHOST ghost)
    {
        _ghost = ghost;
    }
        
    public boolean test(Game game) 
    {
        return game.isGhostEdible(_ghost);
    }
}
