package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class IsPowerPillStillAvailible implements ICondition {
    
    private int _pillIndex;
    
    public IsPowerPillStillAvailible(int pillIndex)
    {
        _pillIndex = pillIndex;
    }
    
    public boolean test(Game game) 
    {
        return game.isPowerPillStillAvailable(_pillIndex);
    }
}
