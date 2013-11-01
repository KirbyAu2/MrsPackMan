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

public class Wander implements IAction, IBinaryNode{
	public MOVE _move = MOVE.NEUTRAL;
	private Hashtable<Integer, Integer> _closeNodes = new Hashtable<Integer, Integer>();
	//private Integer[] startingTargets = new Integer[]{275, 136, 47, 12, 69, 62, 100, 143, 115, 169, 19, 75, 48, 44, 69, 87, 77, 76, 145, 65, 24, 172, 41, 46, 122, 53, 130, 42, 126, 79, 141, 47, 149, 44, 21, 14, 20, 86, 101, 69, 80, 46, 19, 72, 74, 68, 68, 6, 34, 120, 0, 9, 38, 132, 65, 64, 12, 40, 55, 58, 53, 109, 163, 161, 147, 80, 83, 60, 77, 33, 1, 53, 24, 105, 81, 71, 5, 82, 161, 4, 73, 146, 22, 43, 130, 20, 36, 16, 152, 70, 129, 63, 113, 3, 31, 13, 82, 44, 26, 66, 74, 63, 85, 55, 10, 120, 49, 6, 100, 19, 59, 5, 21, 37, 32, 62, 59, 27, 135, 93, 66, 64, 6, 16, 39, 81, 38, 69, 87, 23, 25, 69, 1, 70, 52, 100, 65, 128, 64, 53, 66, 131, 2, 2, 52, 31, 92, 75, 74, 72, 156, 91, 21, 151, 136, 96, 72, 82, 10, 168, 25, 47, 110, 103, 26, 90, 35, 65, 128, 39, 26};
	//private int _startTarget = 275;
	private int _startTarget = 275;
	private int _target = _startTarget;
	private int _targetCount = 1;
	private ArrayList<Integer> _targetList = new ArrayList<Integer>(_startTarget);
	//private ArrayList<Integer> _targetList = new ArrayList<Integer>(Arrays.asList(startingTargets));
	
	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public IAction makeDecision(Game game) {
		// TODO Auto-generated method stub
		int distance = 20;
		int minCost = 100000;
		int bestTarget = 0;
		int equalCount = 0;
		int count = 0;
		int pacmanIndex = game.getPacmanCurrentNodeIndex();
		if(pacmanIndex == _target){
			//_target = getNextTarget(game);
			_target = getRandomTarget(game);
		}
		ArrayList<Integer> leafList = getPacManRadiusPoints(distance, game);
		for(Integer leaf : leafList){
			//color(leaf, count, game);
			count++;
			int cost = _closeNodes.get(leaf);
			//GameView.addPoints(game, Color.MAGENTA, leaf);
			for(GHOST gType:GHOST.values()){
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
			int distanceFromTarget = game.getShortestPathDistance(leaf, _target);
			cost += distanceFromTarget*5;
			//System.out.print(cost+", ");
			if(cost < minCost){
				minCost = cost;
				bestTarget = leaf;
			}
		}
		GameView.addPoints(game, Color.GREEN, _target);
		//GameView.addPoints(game, Color.MAGENTA, bestTarget);
		_closeNodes.clear();
		//System.out.println("\n"+minCost);
		int pacManIndex = game.getPacmanCurrentNodeIndex();
		_move = game.getNextMoveTowardsTarget(pacManIndex, bestTarget, DM.PATH);
		return this;
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
					int ghostMod = 0; //number of ghosts close to pacman
					for(GHOST g:GHOST.values()){
						if(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g)) < 100){
							ghostMod++;
						}
					}
					cost -= 10000*ghostMod;
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
	
	public void color(int index, int num, Game game){
		switch(num%8){
		case 0:
			GameView.addPoints(game, Color.YELLOW, index);
			break;
		case 1:
			GameView.addPoints(game, Color.CYAN, index);
			break;
		case 2:
			GameView.addPoints(game, Color.RED, index);
			break;
		case 3:
			GameView.addPoints(game, Color.BLUE, index);
			break;
		case 4:
			GameView.addPoints(game, Color.GREEN, index);
			break;
		case 5:
			GameView.addPoints(game, Color.MAGENTA, index);
			break;
		case 6:
			GameView.addPoints(game, Color.PINK, index);
			break;
		case 7:
			GameView.addPoints(game, Color.ORANGE, index);
			break;
			
		}
	}

	public void printArrayList(ArrayList<Integer> list){
		for(int n: list){
			System.out.print(n+", ");
		}
		System.out.print("\n");
	}
	
	public int getNextTarget(Game game){
		int ret = 0;
		if(_targetCount >= _targetList.size()){
			ret = getRandomTarget(game);
		}else{
			ret = _targetList.get(_targetCount);
			System.out.println(ret);
		}
		System.out.println(_targetList.size());
		if(game.getScore() > 6000){
			System.out.print("\nScore: "+game.getScore()+", List:");
			printArrayList(_targetList);
		}
		_targetCount++;
		return ret;
	}
	
	public int getRandomTarget(Game game){
		int[] pills = game.getActivePillsIndices();
		int ret = 0;
		int possibleTarget = (int)Math.ceil(Math.random()*pills.length);
		if(possibleTarget < pills.length){
			ret = possibleTarget;
			_targetList.add(_target);
		}
		if(game.getScore() > 6000){
			System.out.print("\nScore: "+game.getScore()+", List:");
			printArrayList(_targetList);
		}
		return ret;
	}
	

	@Override
	public MOVE getMove() {
		// TODO Auto-generated method stub
		return _move;
	}

	@Override
	public MOVE getMove(Game game) {
		// TODO Auto-generated method stub
		makeDecision(game);
		return _move;
	}
	
}
