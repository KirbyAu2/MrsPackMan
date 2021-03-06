package edu.ucsc.gameAI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.Map;

import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
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
	private Hashtable<Integer, Boolean> _leafList = new Hashtable<Integer, Boolean>();
	private int aStarCount = 0;
	private PacAStar _aStar;
	private Boolean initd = false;
	private EnumMap<MOVE, Integer> moveScores =  new EnumMap<MOVE, Integer>(MOVE.class);
	private GetMoveAvoidingGhosts _search = new GetMoveAvoidingGhosts();
	
	public IdentifyClusters(){
		//colorClusters(game);
	}
	
	public void colorClusters(Game game){
		//System.out.println(_clusters.size());
		for(int i=0;i<this._clusters.size();i++){
			int[] colored = new int[this._clusters.get(i).size()];
			int[] leaves = new int[this._leafList.size()];
			int count = 0;
			for (int j=0;j<colored.length;j++){
				//colored[j] = this._clusters.get(0)[j].nodeIndex;
				//System.out.println(this._clusters.get(i).get(j));
				if(this._clusters.get(i).get(j) != null){
					colored[j] = this._clusters.get(i).get(j).nodeIndex;
					if(_leafList.get(colored[j]) != null){
						//System.out.println("color:" +colored[j]);
						leaves[count++] = colored[j];
					}
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
			GameView.addPoints(game, Color.LIGHT_GRAY, leaves);
			//System.out.println("IS ZERO THERE? : " + _leafList.get(0));
		}
	}
	
	private void initCluster(Game game){
		Node[] gameGraph = game.getCurrentMaze().graph;
		this._aStar = new PacAStar();
		this._aStar.createGraph(gameGraph);
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
						//GameView.addPoints(game, Color.YELLOW, tmpNeighbor.nodeIndex);
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
		if(game.getPillIndex(pillIndex) == -1 && game.getPowerPillIndex(pillIndex) == -1) return;
		
		ArrayList<Node> tmpCluster = _clustersTable.get(p.nodeIndex);
		_clusters.remove(tmpCluster);
		MOVE[] moves = MOVE.values();
		EnumMap<MOVE,Integer> neighbors = p.neighbourhood;
		
		for(Integer n : neighbors.values()){
			Node tmpNode = _nodesTable.get(n);
			_leafList.put(n, true);
			for(MOVE move : moves){
				//Delete the neighboring nodes connection to p node 
				if(tmpNode.neighbourhood.containsKey(move)){
					if(tmpNode.neighbourhood.get(move) == pillIndex){
						tmpNode.neighbourhood.remove(move);
					}
				}
			}
		}
		
		
		for(Integer n : neighbors.values()){
			Node tmpNode = _nodesTable.get(n);
			//System.out.println(tmpNode.neighbourhood);
			//System.out.println(pillIndex);
			ArrayList<Node> newCluster = new ArrayList<Node>();
			//System.out.println("neighbor: "+tmpNode.nodeIndex);
			split(tmpNode, newCluster, tmpCluster, game);
			if(newCluster.size() != 0){
				_clusters.add(newCluster);
			}
		}
		//System.out.println("");
		//System.out.println(_clusters.get(0).size()+" "+_clusters.get(1).size());
	}
	
	//old McCluster had a Node Eeeyai Eeeyai yo, and on that Node he had a pill Eeeyai Eeeyai yo.
	private void split(Node n, ArrayList<Node> newCluster, ArrayList<Node> oldMcCluster, Game game){
		if(oldMcCluster.contains(n)){
			oldMcCluster.remove(n);
			newCluster.add(n);
			_clustersTable.remove(n.nodeIndex);
			_clustersTable.put(n.nodeIndex, newCluster);
			//GameView.addPoints(game, Color.GREEN, n.nodeIndex);
		}
		
		for(int neighborIndex: n.neighbourhood.values()){
			Node neighbor = _nodesTable.get(neighborIndex);
			if(oldMcCluster.contains(neighbor)){
				if(neighbor.nodeIndex == 169){
					//System.out.println("169 WAS ADDED TO THIS LIST");
				}
				if(neighbor.nodeIndex == game.getPacmanCurrentNodeIndex()){
					//System.out.println(n.neighbourhood);
					//System.out.println(n.nodeIndex);
					//System.out.println("CURRENT PACMAN INCLUDED IN OLD");
					//return;
				}
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
    	int minDistance = 10000;
    	int bestTarget = 900;
    	int[] shortestPath = new int[1];
    	int pacmanIndex = game.getPacmanCurrentNodeIndex();
    	MOVE lastMove = game.getPacmanLastMoveMade();
    	
    	for(ArrayList<Node> cluster: _clusters){
    		for(Node n: cluster){
    			if(_leafList.get(n.nodeIndex) != null){
    				int[] path = game.getShortestPath(pacmanIndex, n.nodeIndex);
    				//int[] path = game.getShortestPath(pacmanIndex, n.nodeIndex);
    				//System.out.println("length = "+path.length);
    				//GameView.addPoints(game, Color.CYAN, n.nodeIndex);
    				if(path.length < minDistance){
    					if(path.length>0){
    	    				//System.out.println("ADDED length = "+path.length);
    						minDistance = path.length;
        					shortestPath = path;
        					bestTarget = n.nodeIndex;
    					}
    				}
    			}
    		}
    	}
    				
    	this._move = _search.getEvadingMove(bestTarget, game);
   		//GameView.addPoints(game, Color.GREEN, shortestPath); 
		//GameView.addPoints(game, Color.WHITE, bestTarget);

    	//System.out.println("Move : "+this._move+'\n');
        return this;
    }
    
    private MOVE getMoveFromPath(Game game, int[] path){
    	int deltaX = 0;
		int deltaY = 0;
    	if(path.length > 1){
    		deltaX = game.getNodeXCood(path[1])-game.getNodeXCood(path[0]);
    		deltaY = game.getNodeYCood(path[1])-game.getNodeYCood(path[0]);
    	}
    	if(deltaX < 0) return MOVE.LEFT;
    	if(deltaX > 0) return MOVE.RIGHT;
    	if(deltaY < 0) return MOVE.UP;
    	if(deltaX > 0) return MOVE.DOWN;
    	return MOVE.NEUTRAL;
    }

    @Override
    public MOVE getMove() {
        return this._move;
    }

    @Override
    public MOVE getMove(Game game) {
        if(!initd){
            initd = true;
            initCluster(game);
        }
        // TODO Auto-generated method stub
        //if(game.wasPillEaten() || game.wasPowerPillEaten()){
            this.pillEaten(game);
        //}
        this.makeDecision(game);
        return this._move;
    }
}
