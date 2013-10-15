package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class PillWasEaten implements ICondition {
    
    private Game _game;
    
    public PillWasEaten(Game game)
    {
        _game = game;
    }
    
    public boolean test() 
    {
        return _game.wasPillEaten();
    }
}
