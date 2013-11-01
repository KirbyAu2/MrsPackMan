package edu.ucsc.gameAI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map.Entry;

import edu.ucsc.gameAI.decisionTrees.binary.IBinaryDecision;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.internal.Node;
import pacman.game.Constants.GHOST;
import pacman.game.Game;
import pacman.game.GameView;

public class GetMoveAvoidingGhosts{
	public MOVE _move = MOVE.NEUTRAL;
	private Hashtable<Integer, Integer> _closeNodes = new Hashtable<Integer, Integer>();
	
	public MOVE getEvadingMove(int target, Game game) {
		// TODO Auto-generated method stub
		int distance = 20;
		int minCost = 100000;
		int bestTarget = 0;
		int equalCount = 0;
		int count = 0;
		int pacmanIndex = game.getPacmanCurrentNodeIndex();
		ArrayList<Integer> leafList = getPacManRadiusPoints(distance, game);
		for(Integer leaf : leafList){
			//color(leaf, count, game);
			count++;
			int cost = _closeNodes.get(leaf);
			//GameView.addPoints(game, Color.MAGENTA, leaf);
			for(GHOST gType:GHOST.values()){
				if(game.isGhostEdible(gType)) continue;
				int ghostIndex = game.getGhostCurrentNodeIndex(gType);
				MOVE ghostMove = game.getGhostLastMoveMade(gType);
				//System.out.println("Ghost: "+ghostIndex);
				int distFromGhostToLeaf = 100000;
				int[] path = game.getShortestPath(ghostIndex, leaf, ghostMove);
				if(game.getGhostLairTime(gType) <= 0){
					distFromGhostToLeaf = path.length;
				}
				//System.out.println("distFromGhostToLeaf: "+distFromGhostToLeaf);
				if(distFromGhostToLeaf <= distance*2){
					cost += 10000;
					//GameView.addPoints(game, Color.RED, path);
					//GameView.addPoints(game, Color.GRAY, leaf);
				}
				//System.out.print("\n"+gType+": ");
				//System.out.print(cost+", ");
			}
			int distanceFromTarget = game.getShortestPathDistance(leaf, target);
			cost += distanceFromTarget*5;
			//System.out.print(cost+", ");
			if(cost < minCost){
				minCost = cost;
				bestTarget = leaf;
			}
		}
		GameView.addPoints(game, Color.GREEN, target);
		//GameView.addPoints(game, Color.MAGENTA, bestTarget);
		_closeNodes.clear();
		//System.out.println("\n"+minCost);
		int pacManIndex = game.getPacmanCurrentNodeIndex();
		_move = game.getNextMoveTowardsTarget(pacManIndex, bestTarget, DM.PATH);
		return _move;
	}	
	
	private ArrayList<Integer> getPacManRadiusPoints(int distance, Game game){
		ArrayList<Integer> leafList = new ArrayList<Integer>();
		int pacManIndex = game.getPacmanCurrentNodeIndex();
		Node pacman = game.getCurrentMaze().graph[pacManIndex];
		//System.out.println(pacman.nodeIndex);
		//System.out.println("fuck this shit");
		searchNeighbors( pacman, 0, _closeNodes, leafList, distance, game.getPacmanLastMoveMade(),game);
		return leafList;
	}
	
	private boolean contains(int[] array, int goal){
		for(int n:array){
			if(goal == n) return true;
		}
		return false;
	}
	
	private void searchNeighbors(Node n, int parentCost ,Hashtable<Integer, Integer> closeNodes, ArrayList<Integer> leafList,int maxDist, MOVE lastMove,Game game){
		int cost = 100;
		if(closeNodes.get(n.nodeIndex) == null && maxDist>-1){
			if(maxDist != 0){
				if(game.getPillIndex(n.nodeIndex) > -1){
					//cost-=70;
					//GameView.addPoints(game, Color.CYAN, n.nodeIndex);
				}else if(contains(game.getActivePowerPillsIndices(), n.nodeIndex)){
					//cost-=100;
					cost -= 10000;
					//GameView.addPoints(game, Color.GREEN, n.nodeIndex);
				}else{
					//GameView.addPoints(game, Color.getHSBColor((float)(parentCost+cost)/3000, (float)0.5, (float)1.0), n.nodeIndex);
				}
				closeNodes.put(n.nodeIndex, parentCost+cost);
				
				//GameView.addPoints(game, Color.getHSBColor((float)(parentCost+cost)/3000, (float)0.5, (float)1.0), n.nodeIndex);
			}else{
				leafList.add(n.nodeIndex);
				closeNodes.put(n.nodeIndex, parentCost+cost);
			}
		}
		
		if(n.neighbourhood.size() <= 0){
			leafList.add(n.nodeIndex);
			//GameView.addPoints(game, Color.GRAY, n.nodeIndex);
		}
		
		for(Entry<MOVE,Integer> neighborEnum: n.neighbourhood.entrySet()){
//			if(neighborEnum.getKey() == lastMove){
//				cost-=10;
//			}
			int neighborIndex = neighborEnum.getValue();
			Node neighbor = game.getCurrentMaze().graph[neighborIndex];
			if(closeNodes.get(neighbor.nodeIndex) == null && maxDist != 0){
				searchNeighbors(neighbor, parentCost+cost, closeNodes, leafList,maxDist-1, lastMove, game);
			}
		}
		//System.out.println(' ');
	}
	
}
