package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class PacmanInRegion implements ICondition {
    
    private int _maxX, _maxY, _minX,_minY;
    
    public PacmanInRegion(int x1, int y1, int x2, int y2)
    {
        _maxX = (x1 > x2) ? x1 : x2;
        _minX = (x1 > x2) ? x2 : x1;
        _maxY = (y1 > y2) ? y1 : y2;
        _minY = (y1 > y2) ? y2 : y1;
    }
    
    public boolean test(Game game) 
    {
    	int index = game.getPacmanCurrentNodeIndex();
    	int x = game.getNodeXCood(index);
    	int y = game.getNodeYCood(index);
    	boolean insideX = (x <= _maxX && x >= _minX);
    	boolean insideY = (y <= _maxY && y >= _minY);
        return (insideX && insideY);
    }
}
