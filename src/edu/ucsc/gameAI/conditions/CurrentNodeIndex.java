package src.edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class CurrentNodeIndex implements ICondition {
	
    private Game _game;
    private int _min,_max;
    
	public CurrentNodeIndex(Game game, int min, int max)
	{
        _game = game;
        _min = min;
        _max = max;
	}
	
	public boolean test() 
	{
		return false;
	}
}
