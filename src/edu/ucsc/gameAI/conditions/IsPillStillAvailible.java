package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class IsPillStillAvailible implements ICondition {
    
    private int _pillIndex;
    
    public IsPillStillAvailible( int pillIndex)
    {
        _pillIndex = pillIndex;
    }
    
    public boolean test(Game game) 
    {
        return game.isPillStillAvailable(_pillIndex);
    }
}
