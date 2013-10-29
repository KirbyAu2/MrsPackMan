package edu.ucsc.gameAI;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.internal.AStar;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;

public class GhostMoveTowardsNode implements IAction, IBinaryNode {
    private int _target;
    private MOVE _move;
    private AStar _aStar;
    private GHOST _ghost;

    public GhostMoveTowardsNode(Game game, GHOST ghost, int TargetNodeIndex) {
        this._ghost = ghost;
        this._target = TargetNodeIndex;
       // this._aStar = new AStar();
       // this._aStar.createGraph(game.getCurrentMaze().graph);
    }

    public void doAction() {
        // TODO Auto-generated method stub

    }

    public IAction makeDecision() {
        return this;
    }

    // make decision must be called before getMove()
    @Override
    public IAction makeDecision(Game game) {
        //System.out.println("Inside GhostMoveTowards makeDecision");
////////A* option
//    	int startIndex = game.getGhostCurrentNodeIndex(_ghost);
//    	MOVE lastMoveMade = game.getGhostLastMoveMade(_ghost);
//    	int[] path = this._aStar.computePathsAStar(startIndex, this._target, lastMoveMade, game);
//    	this._move = this.getMoveFromPath(game, path);
    	this._move = game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(_ghost), 
    	        game.getPacmanCurrentNodeIndex(), 
    	        game.getGhostLastMoveMade(_ghost), DM.EUCLID);
        return this;
    }

    private MOVE getMoveFromPath(Game game, int[] path) {
        int deltaX = 0;
        int deltaY = 0;
        if (path.length > 1) {
            deltaX = game.getNodeXCood(path[1]) - game.getNodeXCood(path[0]);
            deltaY = game.getNodeYCood(path[1]) - game.getNodeYCood(path[0]);
        }
        if (deltaX < 0)
            return MOVE.LEFT;
        if (deltaX > 0)
            return MOVE.RIGHT;
        if (deltaY < 0)
            return MOVE.UP;
        if (deltaX > 0)
            return MOVE.DOWN;
        return MOVE.NEUTRAL;
    }

    @Override
    public MOVE getMove() {
        return this._move;
    }
    public MOVE getMove(Game game) {  
        this._move = game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(_ghost), 
                game.getPacmanCurrentNodeIndex(), 
                game.getGhostLastMoveMade(_ghost), DM.EUCLID);        
        return this._move;     
    }
}
