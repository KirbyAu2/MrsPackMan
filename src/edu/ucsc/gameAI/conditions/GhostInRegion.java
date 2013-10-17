package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;
import pacman.game.Constants.GHOST;

public class GhostInRegion implements ICondition {
    
    private int _maxX, _minX, _maxY, _minY;
    
    public GhostInRegion(int x1, int y1, int x2, int y2)
    {
        _maxX = (x1 > x2) ? x1 : x2;
        _minX = (x1 > x2) ? x2 : x1;
        _maxY = (y1 > y2) ? y1 : y2;
        _minY = (y1 > y2) ? y2 : y1;
    }
    
    public boolean test(Game game) 
    {
    	for (GHOST g : GHOST.values()) {
    		int index = game.getGhostCurrentNodeIndex(g);
    		int x = game.getNodeXCood(index);
        	int y = game.getNodeYCood(index);
        	boolean insideX = (x <= _maxX && x >= _minX);
        	boolean insideY = (y <= _maxY && y >= _minY);
            if (insideX && insideY) return true;
    	}
        return false;
    }
}
