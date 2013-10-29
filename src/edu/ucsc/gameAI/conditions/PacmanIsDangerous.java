package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.internal.AStar;
import pacman.game.Game;

public class PacmanIsDangerous implements ICondition {
    
    private GHOST _ghost;
    private int _target;
    private AStar _aStar;
    
    public PacmanIsDangerous(GHOST ghost)
    {
        _ghost = ghost;      
    }
    
    public boolean test(Game game) 
    {
        int startIndex = game.getPacmanCurrentNodeIndex();
        MOVE lastMoveMade = game.getPacmanLastMoveMade();
        this._target = game.getGhostCurrentNodeIndex(_ghost);
        this._aStar = new AStar();
        this._aStar.createGraph(game.getCurrentMaze().graph);
        double edibleTime = game.getGhostEdibleTime(_ghost);
        int interceptPath[] =  this._aStar.computePathsAStar(startIndex, this._target, lastMoveMade, game);
        double ediblePathTime = interceptPath.length/edibleTime;
        double interceptTime = (interceptPath.length / 2);
                
        return (interceptTime < edibleTime);
        //return (edibleTime >= 5);
    }
}
