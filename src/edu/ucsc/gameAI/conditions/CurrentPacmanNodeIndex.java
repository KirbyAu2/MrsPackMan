package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class CurrentPacmanNodeIndex implements ICondition {
    
    private Game _game;
    private int _index;

    public CurrentPacmanNodeIndex(Game game, int index)
    {
        _game = game;
        _index = index;
    }
    
    
    public boolean test() 
    {
        return ( _index == _game.getPacmanCurrentNodeIndex() );
    }
}
