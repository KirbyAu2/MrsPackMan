package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;
import pacman.game.Constants.GHOST;

public class GhostNearPacman implements ICondition {
    
    private int _radius;
    
    public GhostNearPacman(int radius)
    {
        _radius = radius;
    }
    
    public boolean test(Game game) 
    {
    	for (GHOST g : GHOST.values()) {
    		int index = game.getGhostCurrentNodeIndex(g);
    		int x = game.getNodeXCood(index);
        	int y = game.getNodeYCood(index);
        	int pacmanIndex = game.getPacmanCurrentNodeIndex();
        	int pX = game.getNodeXCood(pacmanIndex);
        	int pY = game.getNodeYCood(pacmanIndex);
        	boolean insideX = (x <= pX + _radius && x >= pX - _radius);
        	boolean insideY = (y <= pY + _radius && y >= pY - _radius);
            if (insideX && insideY) return true;
    	}
        return false;
    }
}
