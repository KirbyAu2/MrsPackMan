package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class IsPowerPillStillAvailable implements ICondition {
    
    private int _pillIndex;
    
    public IsPowerPillStillAvailable(int pillIndex)
    {
        _pillIndex = pillIndex;
    }
    
    public boolean test(Game game) 
    {
        return game.isPowerPillStillAvailable(_pillIndex);
    }
}
