package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class IsPowerPillStillAvailible implements ICondition {
    
    private Game _game;
    private int _pillIndex;
    
    public IsPowerPillStillAvailible(Game game, int pillIndex)
    {
        _game = game;
        _pillIndex = pillIndex;
    }
    
    public boolean test() 
    {
        return _game.isPowerPillStillAvailable(_pillIndex);
    }
}
