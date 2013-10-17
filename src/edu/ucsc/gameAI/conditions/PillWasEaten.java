package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class PillWasEaten implements ICondition {
    
    public PillWasEaten()
    {
    }
    
    public boolean test(Game game) 
    {
        return game.wasPillEaten();
    }
}
