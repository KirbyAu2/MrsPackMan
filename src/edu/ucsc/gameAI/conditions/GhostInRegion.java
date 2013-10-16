package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class GhostInRegion implements ICondition {
    
    private Game _game;
    private int _x1,_y1,_x2,_y2;
    
    public GhostInRegion(Game game, int x1, int y1, int x2, int y2)
    {
        _game = game;
        _x1 = x1;
        _y1 = y1;
        _x2 = x2;
        _y1 = y1;
    }
    
    public boolean test() 
    {
        return false;
    }
}
