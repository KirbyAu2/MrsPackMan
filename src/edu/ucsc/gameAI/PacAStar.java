package edu.ucsc.gameAI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.PriorityQueue;

import pacman.game.Constants.GHOST;
import pacman.game.Game;
import pacman.game.Constants.MOVE;
import pacman.game.GameView;
import pacman.game.internal.Node;

public class PacAStar{
private N[] graph;
private Hashtable<Integer, Boolean> _closeNodes = new Hashtable<Integer, Boolean>();
private int _count = 0;
	
	public void createGraph(Node[] nodes)
	{
		graph=new N[nodes.length];
		
		//create graph
		for(int i=0;i<nodes.length;i++)
			graph[i]=new N(nodes[i].nodeIndex);
		
		//add neighbours
		for(int i=0;i<nodes.length;i++)
		{	
			EnumMap<MOVE,Integer> neighbours=nodes[i].neighbourhood;
			MOVE[] moves=MOVE.values();
			
			for(int j=0;j<moves.length;j++)
				if(neighbours.containsKey(moves[j]))
					graph[i].adj.add(new E(graph[neighbours.get(moves[j])],moves[j],1));	
		}
	}
	
	public synchronized int[] computePathsAStar(int s, int t, MOVE lastMoveMade, Game game)
    {	
		N start=graph[s];
		N target=graph[t];
		
		_count = 0;
		int ghostDistance = 10;
		//fills _closeNodes with all the nodes close to the ghosts
		for(GHOST type: GHOST.values()){
        	int ghostIndex = game.getGhostCurrentNodeIndex(type);
			Node ghostNode = game.getCurrentMaze().graph[ghostIndex];
			getPointsNearGhost(ghostNode, _closeNodes,  10, game);
        }
		System.out.println(_count);
		
        PriorityQueue<N> open = new PriorityQueue<N>();
        ArrayList<N> closed = new ArrayList<N>();

        start.g = 0;
        start.h = game.getShortestPathDistance(start.index, target.index);

        start.reached=lastMoveMade;
        
        open.add(start);

        while(!open.isEmpty())
        {
            N currentNode = open.poll();
            closed.add(currentNode);
            
            if (currentNode.isEqual(target))
                break;

            for(E next : currentNode.adj)
            {
            	if(next.move!=currentNode.reached.opposite())
            	{
            		if(isNodeClostToGhosts(next.node.index, currentNode.g+next.cost, ghostDistance, game)){
            			next.cost += 10000;
            		}
	                double currentDistance = next.cost;
	
	                if (!open.contains(next.node) && !closed.contains(next.node))
	                {
	                    next.node.g = currentDistance + currentNode.g;
	                    next.node.h = game.getShortestPathDistance(next.node.index, target.index);
	                    next.node.parent = currentNode;
	                    
	                    next.node.reached=next.move;
	
	                    open.add(next.node);
	                }
	                else if (currentDistance + currentNode.g < next.node.g)
	                {
	                    next.node.g = currentDistance + currentNode.g;
	                    next.node.parent = currentNode;
	                    
	                    next.node.reached=next.move;
	
	                    if (open.contains(next.node))
	                        open.remove(next.node);
	
	                    if (closed.contains(next.node))
	                        closed.remove(next.node);
	
	                    open.add(next.node);
	                }
	            }
            }
        }
        _closeNodes.clear();
        return extractPath(target, game);
    }
	
	public synchronized int[] computePathsAStar(int s, int t, Game game)
    {	
		return computePathsAStar(s, t, MOVE.NEUTRAL, game);
    }
	
	private boolean isNodeClostToGhosts(int nodeIndex, double distanceFromPacMan, int ghostDistance, Game game){
		for(GHOST type: GHOST.values()){
        	int ghostIndex = game.getGhostCurrentNodeIndex(type);
			//Node ghostNode = game.getCurrentMaze().graph[ghostIndex];
        	if(_closeNodes.get(nodeIndex) != null && distanceFromPacMan <= ghostDistance+40 && !game.isGhostEdible(type)){
        		return true;
        	}
        }
		return false;
	}
	
	private void getPointsNearGhost(Node n, Hashtable<Integer, Boolean> closeNodes, int maxDist, Game game){
		if(closeNodes.get(n.nodeIndex) == null && maxDist != 0){
			closeNodes.put(n.nodeIndex, true);
			GameView.addPoints(game, Color.RED, n.nodeIndex);
			_count++;
		}
		
		for(int neighborIndex: n.neighbourhood.values()){
			Node neighbor = game.getCurrentMaze().graph[neighborIndex];
			if(closeNodes.get(neighbor.nodeIndex) == null && maxDist != 0){
				getPointsNearGhost(neighbor, closeNodes, maxDist-1, game);
			}
		}
		//System.out.println(' ');
	}

    private synchronized int[] extractPath(N target, Game game)
    {
    	ArrayList<Integer> route = new ArrayList<Integer>();
        N current = target;
        route.add(current.index);

        while (current.parent != null)
        {
            route.add(current.parent.index);
            current = current.parent;
        }
        
        Collections.reverse(route);

        int[] routeArray=new int[route.size()];
        
        for(int i=0;i<routeArray.length;i++)
        	routeArray[i]=route.get(i);
        GameView.addPoints(game, Color.BLUE, routeArray);
        return routeArray;
    }
    
    public void resetGraph()
    {
    	for(N node : graph)
    	{
    		node.g=0;
    		node.h=0;
    		node.parent=null;
    		node.reached=null;
    	}
    }
}

class N implements Comparable<N>
{
    public N parent;
    public double g, h;
    public boolean visited = false;
    public ArrayList<E> adj;
    public int index;
    public MOVE reached=null;

    public N(int index)
    {
        adj = new ArrayList<E>();
        this.index=index;
    }

    public N(double g, double h)
    {
        this.g = g;
        this.h = h;
    }

    public boolean isEqual(N another)
    {
        return index == another.index;
    }

    public String toString()
    {
        return ""+index;
    }

	public int compareTo(N another)
	{
      if ((g + h) < (another.g + another.h))
    	  return -1;
      else  if ((g + h) > (another.g + another.h))
    	  return 1;
		
		return 0;
	}
}

class E
{
	public N node;
	public MOVE move;
	public double cost;
	
	public E(N node,MOVE move,double cost)
	{
		this.node=node;
		this.move=move;
		this.cost=cost;
	}
}

