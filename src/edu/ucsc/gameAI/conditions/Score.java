package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class Score implements ICondition {
	
	Game game;
	
	
	public Score(int min, int max)
	{
		
	}
	
	public boolean test() 
	{
		return false;
	}
}
