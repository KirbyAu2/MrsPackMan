package edu.ucsc.gameAI;

import pacman.game.Constants.MOVE;
import pacman.game.Game;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;

public class NeutralAction implements IAction, IBinaryNode {

	public void doAction() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public IAction makeDecision(Game game) {
		return this;
	}

	@Override
	public MOVE getMove() {
		return MOVE.NEUTRAL;
	}

    @Override
    public MOVE getMove(Game game) {
        // TODO Auto-generated method stub
        return MOVE.NEUTRAL;
    }
}
