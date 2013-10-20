package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class PacmanLastMove implements ICondition {

    private MOVE _move;
    
    public PacmanLastMove(MOVE move)
    {
        _move = move;
    }
    
    public boolean test(Game game) 
    {
        return ( _move == game.getPacmanLastMoveMade() );
    }
}
