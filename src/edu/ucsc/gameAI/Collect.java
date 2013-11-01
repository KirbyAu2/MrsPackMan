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
	private Boolean flipped = false;
		
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
		if (!ghostNearby){
			//this._move = this.evade(game);
			//return this;
		      //closestPill += 1;
	            int spot = (flipped)?4:6;
	            flipped = !flipped;
	            Node n = nodes[closestPill];
	            while(spot > 0){
	                spot--;
	                for(int neighbor : n.neighbourhood.values()){
	                    if(n.neighbourhood.get(MOVE.DOWN) != null){
    	                    if(n.neighbourhood.get(MOVE.DOWN) == neighbor){
    	                        n = nodes[neighbor];
    	                        //spot--;
    	                        continue;
    	                    }
	                    }
	                }
	            }
	            closestPill = n.nodeIndex;
		}else{
		    System.out.println("GHOST IS NEAR");
		}
        GameView.addPoints(game, Color.CYAN, closestPill);
    	MOVE lastMoveMade = game.getPacmanLastMoveMade();
    	int[] path = game.getShortestPath(startIndex, closestPill);
    	//int[] path = game.getShortestPath(startIndex, closestPill, lastMoveMade);
    	this._move = MOVE.NEUTRAL;
    	if(path.length > 1){
    		this._move = game.getNextMoveTowardsTarget(path[0], path[1], DM.EUCLID);
    	}else{
    	    this._move = (game.getPacmanLastMoveMade() == MOVE.DOWN) ? MOVE.UP : MOVE.DOWN;
    	}
    	System.out.println(path.length);
    	System.out.println(this._move);
        return this;
    }
    
    private MOVE evade(Game game) {
    	int pacManIndex = game.getPacmanCurrentNodeIndex();
    	MOVE move;
		GHOST nearestGhost = this.findNearestGhost(game, pacManIndex);
		MOVE ghostDirection = game.getGhostLastMoveMade(nearestGhost);
		MOVE pacmanDirection = game.getPacmanLastMoveMade();
		MOVE direction = this.getMoveFromPoints(game, pacManIndex, game.getGhostCurrentNodeIndex(nearestGhost));
		/*if(direction==MOVE.DOWN  && ghostDirection==MOVE.UP){
			return MOVE.UP;
		}else if(direction==MOVE.UP  && ghostDirection==MOVE.DOWN){
			return MOVE.DOWN;
		}else if(direction==MOVE.RIGHT  && ghostDirection==MOVE.LEFT){
			return MOVE.LEFT;
		}else if(direction==MOVE.LEFT  && ghostDirection==MOVE.RIGHT){
			return MOVE.RIGHT;
		}*/
		int ghostIndex = game.getGhostCurrentNodeIndex(nearestGhost);
		int ghostYCord = game.getNodeYCood(ghostIndex);
		int ghostXCord = game.getNodeXCood(ghostIndex);
		int pacmanYCord = game.getNodeYCood(pacManIndex);
		int pacmanXCord = game.getNodeXCood(pacManIndex);
		if(ghostYCord < pacmanYCord){
			if((ghostDirection == MOVE.DOWN || ghostDirection == MOVE.RIGHT) && 
					(pacmanDirection == MOVE.UP || pacmanDirection == MOVE.LEFT) &&
					(ghostXCord <= pacmanXCord)){
				System.out.println("Evaded");
				return MOVE.RIGHT;
				//return getEvadingMove(game, pacManIndex, ghostIndex, ghostDirection);
			}else if((ghostDirection == MOVE.DOWN || ghostDirection == MOVE.LEFT) && 
					(pacmanDirection == MOVE.UP || pacmanDirection == MOVE.RIGHT) &&
					(ghostXCord >= pacmanXCord)){
				System.out.println("Evaded");
				return MOVE.LEFT;
				//return getEvadingMove(game, pacManIndex, ghostIndex, ghostDirection);
			}
		}else{
			if((ghostDirection == MOVE.UP || ghostDirection == MOVE.RIGHT) && 
					(pacmanDirection == MOVE.DOWN || pacmanDirection == MOVE.LEFT) &&
					(ghostXCord <= pacmanXCord)){
				System.out.println("Evaded");
				return MOVE.DOWN;
				//return getEvadingMove(game, pacManIndex, ghostIndex, ghostDirection);
			}else if((ghostDirection == MOVE.UP || ghostDirection == MOVE.LEFT) && 
					(pacmanDirection == MOVE.DOWN || pacmanDirection == MOVE.RIGHT) &&
					(ghostXCord >= pacmanXCord)){
				System.out.println("Evaded");
				//return getEvadingMove(game, pacManIndex, ghostIndex, ghostDirection);
				return MOVE.UP;
			}
		}
		
		return MOVE.NEUTRAL;
	}
    
    private MOVE getEvadingMove(Game game, int pacMan, int ghost, MOVE lastGhostMoveMade){
    	System.out.println("Pac: "+pacMan+"ghost: "+ghost);
    	int[] path = game.getShortestPath(pacMan, ghost, lastGhostMoveMade);
    	//get the second to last point in the path and go the opposite direction
    	int index = 0;
    	if (path.length > 1){
    		index = path.length-1;
    	}
    	int closestPoint = path[index];
    	return getMoveFromPoints(game, closestPoint, ghost);
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
    		//int[] path = game.getShortestPath(pacManIndex, index, lastMove);
    		int[] path = game.getShortestPath(pacManIndex, index);
    		if(path.length < shortest){
    			shortest = path.length;
    			nearestIndex = index;
    		}
    	}
    	return nearestIndex;
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
    	int	deltaX = game.getNodeXCood(index2)-game.getNodeXCood(index1);
    	int	deltaY = game.getNodeYCood(index2)-game.getNodeYCood(index1);
    	if(deltaX < 0) return MOVE.LEFT;
    	if(deltaX > 0) return MOVE.RIGHT;
    	if(deltaY < 0) return MOVE.UP;
    	if(deltaY > 0) return MOVE.DOWN;
    	return MOVE.NEUTRAL;
    }
    @Override
    public MOVE getMove() {
        return this._move;
    }
    public MOVE getMove(Game game) {
        makeDecision(game);
        return this._move;
    }
}
