package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
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
        this._target = game.getGhostCurrentNodeIndex(_ghost);
        double edibleTime = game.getGhostEdibleTime(_ghost);
        int interceptPath[] = game.getShortestPath(startIndex, this._target,
                lastMoveMade);
        int curLevelSpeed = game.getCurrentLevel();
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
        
        return (unsafePath || pacmanNearPowerPill);
    }
}
