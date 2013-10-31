package edu.ucsc.gameAI;

import edu.ucsc.gameAI.conditions.SafeToPursue;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Constants;
import pacman.game.Game;

public class EatGhosts implements IAction{
    
    MOVE _move;

    public EatGhosts(){
        
    }
    
    private void eat(Game game){
        int targetIndex = -1;
        int minPath = 10000;
        for(GHOST ghost : GHOST.values()){
            //SafeToPursue stp = new SafeToPursue(ghost);
            Boolean ghostIsSafe = true;
            if(game.isGhostEdible(ghost) && ghostIsSafe){
                int[] path = game.getShortestPath(game.getPacmanCurrentNodeIndex(),
                        game.getGhostCurrentNodeIndex(ghost), game.getPacmanLastMoveMade());
                if(path.length < minPath){
                    minPath = path.length;
                    targetIndex = game.getGhostCurrentNodeIndex(ghost);
                    System.out.println("CHASING: " + ghost.name());
                    break;
                }
            }
            //by default just in case
            //targetIndex = game.getGhostCurrentNodeIndex(ghost);
            targetIndex = game.getPacmanCurrentNodeIndex();
        }
        
        _move = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
                targetIndex, Constants.DM.PATH);
    }
    
    @Override
    public void doAction() {
        // TODO Auto-generated method stub
    }

    @Override
    public MOVE getMove() {
        // TODO Auto-generated method stub
        return _move;
    }

    @Override
    public MOVE getMove(Game game) {
        // TODO Auto-generated method stub
        eat(game);
        return _move;
    }

}
