package src.edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class LastMove implements ICondition {
    
    private Game _game;
    private GHOST _ghost;
    private MOVE _move;
    
    public LastMove(Game game, GHOST ghost, MOVE move)
    {
        _game = game;
        _ghost = ghost;
        _move = move;
    }
    
    public boolean test() 
    {
        return (_game.getGhostLastMoveMade(_ghost)== _move);
    }
}
