package src.edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class LastMove implements ICondition {
	
    private Game _game;
    
	public LastMove(Game game, MOVE last)
	{
        _game = game;
	}
	
	public boolean test() 
	{
		return false;
	}
}
