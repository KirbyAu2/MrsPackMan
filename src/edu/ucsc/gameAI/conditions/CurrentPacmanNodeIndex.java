package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class CurrentPacmanNodeIndex implements ICondition {
    
    private int _index;

    public CurrentPacmanNodeIndex(int index)
    {
        _index = index;
    }
    
    
    public boolean test(Game game) 
    {
        return ( _index == game.getPacmanCurrentNodeIndex() );
    }
}
