package edu.ucsc.gameAI;

import com.sun.org.apache.bcel.internal.generic.IDIV;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;

public class EvadePacmanMove implements IAction, IBinaryNode {

    MOVE myMove = MOVE.DOWN;
    GHOST _ghost;
    int currentPacmanQuad;
    int wayPointNode;
    int wayPointQuad;
    private int maxX = 104;
    private int maxY = 116;
    Quadrant quads[];

    public EvadePacmanMove(Game game, GHOST ghost) {
        _ghost = ghost;
        quads = new Quadrant[4];
        quads[0] = new Quadrant(0, 0, 52, 58);
        quads[0].setWaypoint(97);
        quads[1] = new Quadrant(0, 59, 52, 116);
        quads[1].setWaypoint(1143);
        quads[2] = new Quadrant(53, 59, 104, 116);
        quads[2].setWaypoint(1148);
        quads[3] = new Quadrant(53, 0, 104, 58);
        quads[3].setWaypoint(102);
        wayPointQuad = -1;
        currentPacmanQuad = -1;

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
        if(wayPointQuad != -1){
            if(quads[wayPointQuad].isGhostQuadrant(game, _ghost)){
                myMove = game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(_ghost), 
                        quads[wayPointQuad].getWaypoint(), game.getGhostLastMoveMade(_ghost), DM.PATH);
                return myMove;
            }
        }
        // Find pacmans quadrant
        int pacmanCurQuad = -1;
        for (int i = 0; i < 4; i++) {
            if (quads[i].isPacmanQuadrant(game)) {
                pacmanCurQuad = i;
            }
        }
        
        // Find closest ghost to pacman
        int pacmanIndex = game.getPacmanCurrentNodeIndex();
        int ourIndex = game.getGhostCurrentNodeIndex(_ghost);
        int distToPacmanForUs[] = game.getShortestPath(ourIndex,pacmanIndex, 
                game.getPacmanLastMoveMade());
        int dist = distToPacmanForUs.length;
        int rank = 0;
        if (game.getGhostLairTime(_ghost) > 0){
            return MOVE.NEUTRAL;
        }
        for (GHOST ghost : GHOST.values()) {
            if (ghost == _ghost)
                continue;
            if (game.getGhostLairTime(ghost) <= 0) {
                int tempDistArray[] = game.getShortestPath(pacmanIndex,
                        game.getGhostCurrentNodeIndex(ghost), game.getPacmanLastMoveMade());
                //System.out.println("pacDisk "+ tempDistArray.length+ "dist = "+dist);
                if (tempDistArray.length < dist) {
                    rank++;
                }
            }
        }
        if(rank < 0){
            return MOVE.NEUTRAL;
        }
        // set that ghost to move towards the quadrant nearest to them that is
        // not pacmans quadrant
        if (rank == 2 || rank == 3) {
            wayPointQuad = (pacmanCurQuad + 2) % 4;
        } else if (rank == 1) {
            wayPointQuad = (pacmanCurQuad + 1) % 4;
        } else {
            wayPointQuad = (pacmanCurQuad - 1) % 4;
            wayPointQuad = (wayPointQuad < 0) ? 3 : wayPointQuad;
        }
        //System.out.println(_ghost.name()+" Target Quad is " + wayPointQuad);
        //System.out.println("Rank = "+ rank);
        //if(wayPointQuad<0)
        myMove = game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(_ghost), 
                quads[wayPointQuad].getWaypoint(), game.getGhostLastMoveMade(_ghost), DM.MANHATTAN);
        
        return myMove;
    }

    private boolean mostGhostsEdible(Game game) {
        int edibleCount = 0;
        for (GHOST ghost : GHOST.values()) {
            if (game.isGhostEdible(ghost)) {
                edibleCount++;
            }
        }
        if (edibleCount > 2) {
            return true;
        } else {
            return false;
        }

    }

    public boolean aboutToBeEaten(Game game) {
        //System.out.println("_____Inside about to be eaten FUNCTION");
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

        for (GHOST ghost : GHOST.values()) {
            if (game.getGhostLairTime(_ghost) > 0) {
                int startIndex = game.getPacmanCurrentNodeIndex();
                MOVE lastMoveMade = game.getPacmanLastMoveMade();
                int target = game.getGhostCurrentNodeIndex(ghost);
                double edibleTime = game.getGhostEdibleTime(ghost);
                int interceptPath[] = game.getShortestPath(startIndex, target,
                        lastMoveMade);
                int curLevelSpeed = game.getCurrentLevel();
                double ediblePathTime = (edibleTime * 2.1) / 8;

                // return (ediblePathTime > interceptPath.length);
            }
        }
        //System.out.println("Shortest distance is " + shortestDistance);
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
