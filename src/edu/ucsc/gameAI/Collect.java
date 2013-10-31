package edu.ucsc.gameAI;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.internal.AStar;
import edu.ucsc.gameAI.conditions.GhostInRegion;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;

public class Collect implements IAction, IBinaryNode {
	private MOVE _move;
	private PacAStar _aStar;
		
	public Collect(Game game){
		this._aStar = new PacAStar();
		this._aStar.createGraph(game.getCurrentMaze().graph);
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
    	if (closestPill < 0){
    		closestPill = (int)Math.floor(Math.random()*100);
    	}
    	int x = game.getNodeXCood(closestPill);
		int y = game.getNodeYCood(closestPill);
		boolean ghostNearby = this.isGhostNearby(game, startIndex);
		/*if (ghostNearby){
			this._move = this.evade(game);
			return this;
		}*/
    	MOVE lastMoveMade = game.getPacmanLastMoveMade();
    	int[] path = this._aStar.computePathsAStar(startIndex, closestPill, lastMoveMade, game);
    	this._move = MOVE.NEUTRAL;
    	if(path.length > 1){
    		this._move = this.getMoveFromPoints(game, path[0], path[1]);
    	}
        return this;
    }
    
    /*private MOVE evade(Game game) {
    	int pacManIndex = game.getPacmanCurrentNodeIndex();
    	MOVE move;
		//GHOST nearestGhost = this.findNearestGhost(game, pacManIndex);
		//MOVE ghostDirection = game.getGhostLastMoveMade(nearestGhost);
		//MOVE pacmanDirection = game.getPacmanLastMoveMade();
		//MOVE direction = this.getMoveFromPoints(game, pacManIndex, game.getGhostCurrentNodeIndex(nearestGhost));
		/*if(direction==MOVE.DOWN  && ghostDirection==MOVE.UP){
			return MOVE.UP;
		}else if(direction==MOVE.UP  && ghostDirection==MOVE.DOWN){
			return MOVE.DOWN;
		}else if(direction==MOVE.RIGHT  && ghostDirection==MOVE.LEFT){
			return MOVE.LEFT;
		}else if(direction==MOVE.LEFT  && ghostDirection==MOVE.RIGHT){
			return MOVE.RIGHT;
		}
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
    	int[] path = this._aStar.computePathsAStar(ghost, pacMan, game);
    	//get the second to last point in the path and go the opposite direction
    	int index = 0;
    	if (path.length > 1){
    		index = path.length-1;
    	}
    	int closestPoint = path[index];
    	return getMoveFromPoints(game, closestPoint, ghost);
    }*/

	private boolean isGhostNearby(Game game, int pacManIndex) {
    	int x = game.getNodeXCood(pacManIndex);
    	int y = game.getNodeYCood(pacManIndex);
    	int range = 20;
		ICondition ghostInRegion = new GhostInRegion(x-range, y-range, x+range, y+range);
		return ghostInRegion.test(game);
	}

	private int findNearestPowerPill(Game game, int pacManIndex) {
    	int[] pillIndices = game.getActivePowerPillsIndices();
    	int shortest = 10000;
    	int nearestIndex = -1;
    	for (int index : pillIndices){
    		int[] path = _aStar.computePathsAStar(pacManIndex, index, game);
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
    	for (GHOST g : GHOST.values()){
    		int index = game.getGhostCurrentNodeIndex(g);
    		int[] path = _aStar.computePathsAStar(pacManIndex, index, game);
    		if(path.length < shortest){
    			shortest = path.length;
    			nearestGhost = g;
    		}
    	}
    	return nearestGhost;
	}

	private MOVE getMoveFromPoints(Game game, int index1, int index2){
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
        return this._move;
    }
}
