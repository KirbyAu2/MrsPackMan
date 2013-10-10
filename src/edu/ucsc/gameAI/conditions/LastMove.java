package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.MOVE;


public class LastMove implements ICondition {
	

	public LastMove(MOVE last)
	{

	}
	
	public boolean test() 
	{
		return false;
	}
}
