package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class IsPillStillAvailable implements ICondition {
    
    private int _pillIndex;
    
    public IsPillStillAvailable(int pillIndex)
    {
        _pillIndex = pillIndex;
    }
    
    public boolean test(Game game) 
    {
        return game.isPillStillAvailable(_pillIndex);
    }
}
