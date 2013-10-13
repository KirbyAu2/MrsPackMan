package src.edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class MazeIndex implements ICondition {
	
    private Game _game;
    private int _index;
    
	public MazeIndex(Game game, int index)
	{
        _game = game;
        _index = index;
	}
	
	public boolean test() 
	{
		return (_game.getMazeIndex() == _index);
	}
}
