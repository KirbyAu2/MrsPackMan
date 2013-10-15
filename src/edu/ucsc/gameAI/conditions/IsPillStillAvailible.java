package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class IsPillStillAvailible implements ICondition {
    
    private Game _game;
    private int _pillIndex;
    
    public IsPillStillAvailible(Game game, int pillIndex)
    {
        _game = game;
        _pillIndex = pillIndex;
    }
    
    public boolean test() 
    {
        return _game.isPillStillAvailable(_pillIndex);
    }
}
