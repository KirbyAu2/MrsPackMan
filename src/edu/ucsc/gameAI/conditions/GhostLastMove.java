package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GhostLastMove implements ICondition {
    
    private GHOST _ghost;
    private MOVE _move;
    
    public GhostLastMove(GHOST ghost, MOVE move)
    {
        _ghost=ghost;
        _move = move;
    }
    
    public boolean test(Game game) 
    {
        return ( _move == game.getGhostLastMoveMade(_ghost) );
    }
}
