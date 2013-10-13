package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class PowerPillWasEaten implements ICondition {
    
    private Game _game;
    
    public PowerPillWasEaten(Game game)
    {
        _game = game;
    }
    
    public boolean test() 
    {
        return _game.wasPowerPillEaten();
    }
}
