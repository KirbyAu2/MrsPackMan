package edu.ucsc.gameAI;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;

public class EvadePacmanMove implements IAction, IBinaryNode {

    MOVE myMove = MOVE.DOWN;
    GHOST _ghost;

    public EvadePacmanMove(Game game, GHOST ghost) {
        _ghost = ghost;

        myMove = game.getApproximateNextMoveAwayFromTarget(
                game.getGhostCurrentNodeIndex(ghost),
                game.getPacmanCurrentNodeIndex(),
                game.getGhostLastMoveMade(ghost), DM.EUCLID);
    }

    public void doAction() {
        // TODO Auto-generated method stub

    }

    public IAction makeDecision() {
        return this;
    }

    @Override
    public IAction makeDecision(Game game) {

        return this;
    }

    @Override
    public MOVE getMove() {
        return myMove;
    }

    public MOVE getMove(Game game) {
        MOVE evadeMove;
        MOVE interceptMove;
        myMove = game.getApproximateNextMoveAwayFromTarget(
                game.getGhostCurrentNodeIndex(_ghost),
                game.getPacmanCurrentNodeIndex(),
                game.getGhostLastMoveMade(_ghost), DM.EUCLID);

        if (aboutToBeEaten(game)) {
            System.out.println("_____Inside about to be eaten");
            // Set the next move away, so that this ghost won't follow the ghost
            // pack
            evadeMove = myMove;
            // Find the next move towards pacman, so that the ghost can avoid a
            // direct intercept
            interceptMove = game.getNextMoveTowardsTarget(
                    game.getPacmanCurrentNodeIndex(),
                    game.getGhostCurrentNodeIndex(_ghost), DM.MANHATTAN);
            MOVE chaseLeader = game.getNextMoveTowardsTarget(
                    game.getGhostCurrentNodeIndex(_ghost),
                    game.getGhostCurrentNodeIndex(GHOST.SUE), DM.MANHATTAN);
            myMove = interceptMove;
            MOVE pinkyMove = game.getGhostLastMoveMade(GHOST.PINKY);
           /* switch (evadeMove) {
            case UP:
                if (interceptMove == MOVE.LEFT) {
                    myMove = MOVE.RIGHT;
                } else {
                    myMove = MOVE.LEFT;
                }
                break;
            case DOWN:
                if (interceptMove == MOVE.LEFT) {
                    myMove = MOVE.RIGHT;
                } else {
                    myMove = MOVE.LEFT;
                }
                break;
            case LEFT:
                if (interceptMove == MOVE.UP) {
                    myMove = MOVE.DOWN;
                } else {
                    myMove = MOVE.UP;
                }
                break;
            case RIGHT:
                if (interceptMove == MOVE.UP) {
                    myMove = MOVE.DOWN;
                } else {
                    myMove = MOVE.UP;
                }
                break;
            default:
                // If not able to take a different move, 
                //  make the move the ghost was going to do already
                myMove = evadeMove;
                break;
            }*/
        }
        return myMove;
    }

    public boolean aboutToBeEaten(Game game) {
        System.out.println("_____Inside about to be eaten FUNCTION");
        boolean selfSacrafice = true;
        // Find out if this ghost is the closest
        int ghostIndices[] = new int[4];
        ghostIndices[0] = game.getGhostCurrentNodeIndex(GHOST.BLINKY);
        ghostIndices[1] = game.getGhostCurrentNodeIndex(GHOST.PINKY);
        ghostIndices[2] = game.getGhostCurrentNodeIndex(GHOST.INKY);
        ghostIndices[3] = game.getGhostCurrentNodeIndex(GHOST.SUE);
        int shortestDistance = 10000;
        int shortestIndex = 0;
        int ghostIndex = -1;
        int distanceChecked;
        // Check the distance to each ghost using the same search algoritm
        // StarterPacman uses to hunt ghosts
        for (int i = 0; i < 4; i++) {
            distanceChecked = game.getShortestPathDistance(
                    game.getPacmanCurrentNodeIndex(), ghostIndices[i]);
            System.out.println("distance checked is "+distanceChecked);
            if (distanceChecked < shortestDistance) {
                shortestIndex = ghostIndices[i];
                shortestDistance = distanceChecked;
                ghostIndex = i;
            }
        }
        System.out.println("Shortest distance is "+shortestDistance);
        // If this ghost is not the closest to pacman, return false
        switch (ghostIndex) {
        case 0:
            if (_ghost != GHOST.BLINKY)
                return false;
            break;
        case 1:
            if (_ghost != GHOST.PINKY)
                return false;
            break;
        case 2:
            if (_ghost != GHOST.INKY)
                return false;
            break;
        case 3:
            if (_ghost != GHOST.SUE)
                return false;
            break;
        }
        // Find the direct move away from pacman to make sure this ghost doesn't
        // follow the pack of ghosts
        return selfSacrafice;
    }
}
