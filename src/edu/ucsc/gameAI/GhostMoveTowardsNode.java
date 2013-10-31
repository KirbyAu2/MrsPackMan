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
        this._move = MOVE.NEUTRAL;
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
        this._move = game.getApproximateNextMoveTowardsTarget(
                game.getGhostCurrentNodeIndex(_ghost),
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
        int curPacmanIndex = game.getPacmanCurrentNodeIndex();
        int curGhostIndex = game.getGhostCurrentNodeIndex(_ghost);
        MOVE lastPacmanMove = game.getPacmanLastMoveMade();
        MOVE lastGhostMove = game.getGhostLastMoveMade(_ghost);
        int closeEnough = 70;

        // If the first or last ghost in the pack, move directly to PacMan
        //if (_ghost.name() == "PINKY" || _ghost.name() == "BLINKY") {          
            //this._move = game.getNextMoveTowardsTarget(curGhostIndex,
            //        curPacmanIndex, lastGhostMove, DM.MANHATTAN);
             this._move = game.getNextMoveTowardsTarget(curGhostIndex,
             curPacmanIndex, lastGhostMove, DM.EUCLID);
        //} else {
        //    this._move = game.getNextMoveTowardsTarget(curGhostIndex,
        //            curPacmanIndex, lastGhostMove, DM.MANHATTAN);
            
        //}
            
          /*  // Move the other ghost into a flanking position
                // Get the current powerpills that pacman will go after
            int pillIndices[] = game.getActivePowerPillsIndices();

            if (pillIndices.length == 0) {// If no powerPills are left, just
                                          // make the direct move
                this._move = game
                        .getApproximateNextMoveTowardsTarget(curGhostIndex,
                                curPacmanIndex, lastGhostMove, DM.EUCLID);
            } else {// Find the nearest powerPill and move towards it
                int shortestPath = 9000;
                int shortestInt = -1;
                int dist = 0;
                for (int i = 0; i < pillIndices.length; i++) {
                    dist = game.getShortestPathDistance(curPacmanIndex,
                            pillIndices[i], lastPacmanMove);
                    if (dist < shortestPath) {
                        shortestPath = dist;
                        shortestInt = i;
                    }
                }
                System.out.println("Shortest Path is "+shortestPath);
                int ghostToPillDist = game.getShortestPathDistance(curGhostIndex, 
                        pillIndices[shortestInt], lastGhostMove);
                int ghostToPacmanDist = game.getShortestPathDistance(curGhostIndex, 
                        pillIndices[shortestInt], lastGhostMove);
                if((ghostToPillDist > ghostToPacmanDist) || 
                        (ghostToPacmanDist < closeEnough) || 
                        (ghostToPillDist < closeEnough)){
                    // If ghost is close enough to pacman's next powerPill
                    // break off to chase pacman instead
                    this._move = game.getNextMoveTowardsTarget(curGhostIndex,
                            curPacmanIndex, lastGhostMove, DM.EUCLID);
                }// Otherwise go towards the powerpill
                this._move = game.getNextMoveTowardsTarget(curGhostIndex,
                        pillIndices[shortestInt], DM.EUCLID); 
            }
        }*/
        return this._move;
    }
}
