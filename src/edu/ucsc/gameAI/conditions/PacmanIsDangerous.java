package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.internal.AStar;
import pacman.game.Game;
import pacman.game.Constants;

public class PacmanIsDangerous implements ICondition {

    private GHOST _ghost;
    private int _target;

    public PacmanIsDangerous(GHOST ghost) {
        _ghost = ghost;
    }

    public boolean test(Game game) {
        boolean unsafePath = false;
        boolean pacmanNearPowerPill = false;
        int unsafePowerPillDistance = 15;
        // Check to see if ghost will be edible by the time they meet
        // AKA compute if path is safe
        int startIndex = game.getPacmanCurrentNodeIndex();
        MOVE lastMoveMade = game.getPacmanLastMoveMade();
        int target = game.getGhostCurrentNodeIndex(this._ghost);
        double edibleTime = game.getGhostEdibleTime(this._ghost);
        System.out.println("startIndex is: "+startIndex);
        System.out.println("Target is: "+target);
        System.out.println("LastMove is: "+lastMoveMade);
        int interceptPath[];
        interceptPath = game.getShortestPath(target, startIndex,
                lastMoveMade);
        //double interceptPath = game.getDistance(startIndex, target, DM.MANHATTAN);
        double ediblePathTime = (edibleTime * 2.1) / 8;
        unsafePath = (ediblePathTime > interceptPath.length/2);

        // Check to see if pacman is close to powerpill
        int pillIndices[] = game.getActivePowerPillsIndices();
        int shortestDist = 1000;
        for (int i = 0; i < pillIndices.length; i++) {
            int shortestPath = game.getShortestPathDistance(
                    game.getPacmanCurrentNodeIndex(), pillIndices[i]);
            if(shortestDist > shortestPath)
                shortestDist = shortestPath;
        }
        if(shortestDist < unsafePowerPillDistance)
            pacmanNearPowerPill = true;
        
        //return (unsafePath || pacmanNearPowerPill);
        return unsafePath;
    }
}
