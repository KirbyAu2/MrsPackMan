package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.internal.AStar;
import pacman.game.Game;

public class SafeToPursue implements ICondition {

    private GHOST _ghost;
    private int _target;
    private AStar _aStar;

    public SafeToPursue(GHOST ghost) {
        _ghost = ghost;
    }

    public boolean test(Game game) {
        int startIndex = game.getPacmanCurrentNodeIndex();
        MOVE lastMoveMade = game.getPacmanLastMoveMade();
        this._target = game.getGhostCurrentNodeIndex(_ghost);
        // this._aStar = new AStar();
        // this._aStar.createGraph(game.getCurrentMaze().graph);
        int edibleTime = game.getGhostEdibleTime(_ghost);
        double manhattenDistance = game.getDistance(
                game.getPacmanCurrentNodeIndex(),
                game.getGhostCurrentNodeIndex(_ghost), DM.MANHATTAN);
        int interceptTime = ((int) (manhattenDistance / 2));
        // System.out.println(edibleTime);
        // int interceptPath[] = this._aStar.computePathsAStar(startIndex,
        // this._target, lastMoveMade, game);
        int pillTargets[] = game.getPowerPillIndices();
        int nearestIndex;
        double nearestPillDistance = 1000;
        int powerIndex = 0;
        for (int i = 0; i < pillTargets.length; i++) {
            // Find distance to each powerpill
            powerIndex = game.getPowerPillIndex(pillTargets[i]);
            if (powerIndex != -1) {
                if (game.isPowerPillStillAvailable(powerIndex)) {
                    //System.out.println("inside distance loop in SafeToPursue");
                    // If the powerpill is still availible, check the distance
                    // to it
                    //int PillPath[] = this._aStar.computePathsAStar(startIndex,
                    //        pillTargets[i], lastMoveMade, game);
                    double powerPillPath = game.getDistance(startIndex, powerIndex, lastMoveMade, DM.MANHATTAN);
                    if (powerPillPath < nearestPillDistance) {
                        nearestPillDistance = powerPillPath;
                    }
                }
            }
        }
        boolean movingToPowerPill = (game.getPacmanLastMoveMade() == game.getNextMoveTowardsTarget(startIndex, powerIndex, DM.MANHATTAN) );
        //boolean powerPillDanger = (nearestPillDistance < manhattenDistance);
        boolean powerPillDanger = ((nearestPillDistance < 10) && (movingToPowerPill));
        boolean notEdible = (edibleTime == 0);
        return (!powerPillDanger && notEdible);
        // int interceptTime = interceptPath.length / 40 ;
        // return (interceptTime >= edibleTime);
        // return (edibleTime == 0);
    }
}
