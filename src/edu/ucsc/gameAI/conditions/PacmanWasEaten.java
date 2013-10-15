package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class PacmanWasEaten implements ICondition {
    
    private Game _game;
    
    public PacmanWasEaten(Game game)
    {
        _game = game;
    }
    
    public boolean test() 
    {
        return _game.wasPacManEaten();
    }
}
