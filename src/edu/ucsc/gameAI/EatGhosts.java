package edu.ucsc.gameAI;

import java.awt.Color;

import edu.ucsc.gameAI.conditions.SafeToPursue;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

public class EatGhosts implements IAction{
    
    MOVE _move;
    boolean _initd;
    private PacAStar _aStar;
    private GetMoveAvoidingGhosts _search = new GetMoveAvoidingGhosts();
    
    public EatGhosts(){
        
    }
    
    private void initAStar(Game game){
		_aStar = new PacAStar();
		_aStar.createGraph(game.getCurrentMaze().graph);
    }
    
    private void eat(Game game){
        int targetIndex = -1;
        int minPath = 10000;
        for(GHOST ghost : GHOST.values()){
            //SafeToPursue stp = new SafeToPursue(ghost);
            Boolean ghostIsSafe = true;
            if(game.isGhostEdible(ghost) && ghostIsSafe){
                int[] path = game.getShortestPath(game.getPacmanCurrentNodeIndex(),
                									  game.getGhostCurrentNodeIndex(ghost));
                if(path.length < minPath){
                    minPath = path.length;
                    targetIndex = game.getGhostCurrentNodeIndex(ghost);
                    //System.out.println("CHASING: " + ghost.name());
                    GameView.addPoints(game, Color.GRAY, targetIndex);
                }
            }
            //by default just in case
            //targetIndex = game.getGhostCurrentNodeIndex(ghost);
           // targetIndex = game.getPacmanCurrentNodeIndex();
        }
        
        _move = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
                targetIndex, Constants.DM.PATH);
        _move = _search.getEvadingMove(targetIndex, game);
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
    	if(!_initd){
    		initAStar(game);
    	}
        eat(game);
        return _move;
    }

}
