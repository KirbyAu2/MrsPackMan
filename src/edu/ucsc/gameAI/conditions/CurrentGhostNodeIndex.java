package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class CurrentGhostNodeIndex implements ICondition {
    
    private GHOST _ghost;
    private int _index;

    public CurrentGhostNodeIndex(GHOST ghost, int index)
    {
        _ghost = ghost;
        _index = index;
    }
    
    
    public boolean test(Game game) 
    {
        return ( _index == game.getGhostCurrentNodeIndex(_ghost) );
    }
}
