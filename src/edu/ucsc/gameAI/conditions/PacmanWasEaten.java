package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class PacmanWasEaten implements ICondition {
    
    private Game _game;
    
    public PacmanWasEaten()
    {
    }
    
    public boolean test(Game game) 
    {
        return game.wasPacManEaten();
    }
}
