package edu.ucsc.gameAI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.Map;

import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;
import pacman.game.internal.AStar;
import pacman.game.internal.Node;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;

public class IdentifyClusters implements IAction, IBinaryNode {
	private MOVE _move;
	private ArrayList<ArrayList<Node>> _clusters = new ArrayList<ArrayList<Node>>();
	private Hashtable<Integer,Node> _nodesTable = new Hashtable<Integer,Node>();
	private Hashtable<Integer,ArrayList<Node>> _clustersTable = new Hashtable<Integer,ArrayList<Node>>();
		
	public IdentifyClusters(Game game){
		initCluster(game);
		colorClusters(game);
	}
	
	public void colorClusters(Game game){
		//System.out.println(_clusters.size());
		for(int i=0;i<this._clusters.size();i++){
			int[] colored = new int[this._clusters.get(i).size()];
			for (int j=0;j<colored.length;j++){
				//colored[j] = this._clusters.get(0)[j].nodeIndex;
				//System.out.println(this._clusters.get(i).get(j));
				if(this._clusters.get(i).get(j) != null){
					colored[j] = this._clusters.get(i).get(j).nodeIndex;
				}
			}
			switch(i%8){
				case 0:
					GameView.addPoints(game, Color.YELLOW, colored);
					break;
				case 1:
					GameView.addPoints(game, Color.CYAN, colored);
					break;
				case 2:
					GameView.addPoints(game, Color.RED, colored);
					break;
				case 3:
					GameView.addPoints(game, Color.BLUE, colored);
					break;
				case 4:
					GameView.addPoints(game, Color.GREEN, colored);
					break;
				case 5:
					GameView.addPoints(game, Color.MAGENTA, colored);
					break;
				case 6:
					GameView.addPoints(game, Color.PINK, colored);
					break;
				case 7:
					GameView.addPoints(game, Color.ORANGE, colored);
					break;
					
			}
		}
	}
	
	private void initCluster(Game game){
		Node[] gameGraph = game.getCurrentMaze().graph;
		ArrayList<Node> tmpGraph = new ArrayList<Node>();
		MOVE[] moves = MOVE.values();
		for(int i=0;i<gameGraph.length;i++){
			Node n = gameGraph[i];
			int[] neighborhood = {-1,-1,-1,-1};
			for(int j=0;j<4;j++){
				if(n.neighbourhood.containsKey(moves[j])){
					int neighborIndex = n.neighbourhood.get(moves[j]);
					Node tmpNeighbor = gameGraph[neighborIndex];
					for(int k=0;k<3;k++){
						if(tmpNeighbor.neighbourhood.get(moves[j]) != null){
							//Doing a ray cast to see where the next pill is
							//This is in relation to the current direction moves[j]
							
							//Always stepping forward one step
							neighborIndex = tmpNeighbor.neighbourhood.get(moves[j]);
							tmpNeighbor = gameGraph[neighborIndex];
							
							if(tmpNeighbor.pillIndex > -1 
									|| tmpNeighbor.powerPillIndex > -1){
								//Breaks out of loop when hitting a pill
								break;
							}
							
						}else{
							//We ran into a null spot
							break;
						}
					}
					if(tmpNeighbor.pillIndex > -1
							|| tmpNeighbor.powerPillIndex > -1){
						GameView.addPoints(game, Color.YELLOW, tmpNeighbor.nodeIndex);
						//Adds neighbor to neighborhood if it contains a pill
						neighborhood[j] = tmpNeighbor.nodeIndex;
					}
				}
			}
			Node tmp = new Node(n.nodeIndex, n.x, n.y,n.pillIndex, n.powerPillIndex, neighborhood);
			if(tmp.pillIndex > -1 || tmp.powerPillIndex > -1){
				tmpGraph.add(tmp);
				_nodesTable.put(n.nodeIndex, tmp);
				_clustersTable.put(n.nodeIndex, tmpGraph);
			}
		}
		this._clusters.add(tmpGraph);
	}
	
	public void pillEaten(Game game){
		int pillIndex = game.getPacmanCurrentNodeIndex();
		Node p = _nodesTable.get(pillIndex);
		//if its a powerpill, get the fuck out
		//if(game.getPowerPillIndex(pillIndex) > -1) return;
		
		ArrayList<Node> tmpCluster = _clustersTable.get(p.nodeIndex);
		_clusters.remove(tmpCluster);
		MOVE[] moves = MOVE.values();
		EnumMap<MOVE,Integer> neighbors = p.neighbourhood;
		for(Integer n : neighbors.values()){
			Node tmpNode = _nodesTable.get(n);
			for(MOVE move : moves){
				//Delete the neighboring nodes connection to p node 
				if(tmpNode.neighbourhood.containsKey(move)){
					if(tmpNode.neighbourhood.get(move) == pillIndex){
						tmpNode.neighbourhood.remove(move);
					}
				}
			}
			ArrayList<Node> newCluster = new ArrayList<Node>();
			split(tmpNode, newCluster, tmpCluster, game);
			if(newCluster.size() != 0){
				System.out.println(newCluster);
				_clusters.add(newCluster);
			}else{
				//System.out.println(newCluster);
			}
		}
		//System.out.println(_clusters.get(0).size()+" "+_clusters.get(1).size());
	}
	
	//old McCluster had a Node Eeeyai Eeeyai yo, and on that Node he had a pill Eeeyai Eeeyai yo.
	private void split(Node n, ArrayList<Node> newCluster, ArrayList<Node> oldMcCluster, Game game){
		if(oldMcCluster.contains(n)){
			oldMcCluster.remove(n);
			newCluster.add(n);
			_clustersTable.remove(n.nodeIndex);
			_clustersTable.put(n.nodeIndex, newCluster);
			GameView.addPoints(game, Color.GREEN, n.nodeIndex);
		}else{
			System.out.println("NO MCCLUSTER OK!?!?");
		}
		
		for(int neighborIndex: n.neighbourhood.values()){
			Node neighbor = _nodesTable.get(neighborIndex);
			if(oldMcCluster.contains(neighbor)){
				split(neighbor, newCluster, oldMcCluster, game);
			}
		}
		
	}
	
    public void doAction() {
        // TODO Auto-generated method stub
        
    }
    
    public IAction makeDecision() {return this;}

    //make decision must be called before getMove()
    @Override
    public IAction makeDecision(Game game) {
        return this;
    }

    @Override
    public MOVE getMove() {
        return this._move;
    }
}
