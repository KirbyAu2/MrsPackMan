package edu.ucsc.gameAI;

import java.awt.Color;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;
import pacman.game.internal.AStar;
import pacman.game.internal.Node;
import edu.ucsc.gameAI.conditions.GhostInRegion;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;

public class Collect implements IAction, IBinaryNode {
	private MOVE _move;
	private PacAStar _aStar;
	private static int count = 0;
	private boolean _initd = false;
	private Boolean flipped = false;
	private GetMoveAvoidingGhosts _search = new GetMoveAvoidingGhosts();
		
	public Collect(){

	}
	
    public void doAction() {
        // TODO Auto-generated method stub
        
    }
    
    public IAction makeDecision() {return this;}

    //make decision must be called before getMove()
    @Override
    public IAction makeDecision(Game game) {
    	int startIndex = game.getPacmanCurrentNodeIndex();
    	int closestPill = this.findNearestPowerPill(game, startIndex);
    	/*if (closestPill < 0){
    		closestPill = (int)Math.floor(Math.random()*100);
    	}*/
        Node[] nodes = game.getCurrentMaze().graph;

		boolean ghostNearby = this.isGhostNearby(game, startIndex);
        //GameView.addPoints(game, Color.CYAN, closestPill);
    	MOVE lastMoveMade = game.getPacmanLastMoveMade();
    	int[] path = game.getShortestPath(startIndex, closestPill);
    	this._move = MOVE.NEUTRAL;
    	if(path.length > 10){
    		this._move = _search.getEvadingMove(closestPill, game);
    	}else if(!ghostNearby){
    		int abovePill = getPowerPillNeighbor(closestPill, MOVE.UP, game);
    		int belowPill = getPowerPillNeighbor(closestPill, MOVE.DOWN, game);
    		int target = game.getShortestPathDistance(startIndex, abovePill) > 
    					 game.getShortestPathDistance(startIndex, belowPill) ? belowPill : abovePill;
    		this._move = _search.getEvadingMove(target, game);
    	}else{
    		this._move = _search.getEvadingMove(closestPill, game);
    	}
        return this;
    }
    

	private boolean isGhostNearby(Game game, int pacManIndex) {
    	int x = game.getNodeXCood(pacManIndex);
    	int y = game.getNodeYCood(pacManIndex);
    	int range = 20;
		ICondition ghostInRegion = new GhostInRegion(x-range, y-range, x+range, y+range);
		return ghostInRegion.test(game);
	}

	private int findNearestPowerPill(Game game, int pacManIndex) {
    	int[] pillIndices = game.getActivePowerPillsIndices();
    	MOVE lastMove = game.getPacmanLastMoveMade();
    	int shortest = 10000;
    	int nearestIndex = -1;
    	for (int index : pillIndices){
    		int[] path = game.getShortestPath(pacManIndex, index);
    		if(path.length < shortest){
    			shortest = path.length;
    			nearestIndex = index;
    		}
    	}
    	return nearestIndex;
	}
	
	private int getPowerPillNeighbor(int pillIndex, MOVE move, Game game){
		 int spot = 4;
         Node[] nodes = game.getCurrentMaze().graph;
         Node n = nodes[pillIndex];
         while(spot > 0){
             spot--;
             for(int neighbor : n.neighbourhood.values()){
                 if(n.neighbourhood.get(move) != null){
	                    if(n.neighbourhood.get(move) == neighbor){
	                        n = nodes[neighbor];
	                        //spot--;
	                        continue;
	                    }
                 }
             }
         }
         return n.nodeIndex;
	}
	
	private GHOST findNearestGhost(Game game, int pacManIndex) {
    	int shortest = 10000;
    	GHOST nearestGhost = null;
    	MOVE lastMove = game.getPacmanLastMoveMade();
    	for (GHOST g : GHOST.values()){
    		int index = game.getGhostCurrentNodeIndex(g);
    		int[] path = game.getShortestPath(pacManIndex, index, lastMove);
    		if(path.length < shortest){
    			shortest = path.length;
    			nearestGhost = g;
    		}
    	}
    	return nearestGhost;
	}

	private MOVE getMoveFromPoints(Game game, int index1, int index2){
		//GameView.addPoints(game, Color.WHITE, index1);
		//GameView.addPoints(game, Color.WHITE, index2);
		return game.getNextMoveTowardsTarget(index1, index2, DM.PATH);
    	/*int	deltaX = game.getNodeXCood(index2)-game.getNodeXCood(index1);
    	int	deltaY = game.getNodeYCood(index2)-game.getNodeYCood(index1);
    	if(deltaX < 0) return MOVE.LEFT;
    	if(deltaX > 0) return MOVE.RIGHT;
    	if(deltaY < 0) return MOVE.UP;
    	if(deltaY > 0) return MOVE.DOWN;*/
    	//return MOVE.NEUTRAL;
    }
    @Override
    public MOVE getMove() {
        return this._move;
    }
    public MOVE getMove(Game game) {
    	if(_initd == false){
    		_initd = true;
    	}
        makeDecision(game);
        return this._move;
    }
}
