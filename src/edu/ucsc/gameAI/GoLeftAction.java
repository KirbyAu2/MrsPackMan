package edu.ucsc.gameAI;

import pacman.game.Constants.MOVE;
import pacman.game.Game;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;

public class GoLeftAction implements IAction, IBinaryNode {

    public void doAction() {
        // TODO Auto-generated method stub
        
    }
    
    public IAction makeDecision() {return this;}

    @Override
    public IAction makeDecision(Game game) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public MOVE getMove() {
        return MOVE.LEFT;
    }
    public MOVE getMove(Game game) {
        return MOVE.LEFT;
    }
}
