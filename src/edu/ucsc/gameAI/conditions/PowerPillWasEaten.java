package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class PowerPillWasEaten implements ICondition {
       
    public PowerPillWasEaten()
    {
    }
    
    public boolean test(Game game) 
    {
        return game.wasPowerPillEaten();
    }
}
