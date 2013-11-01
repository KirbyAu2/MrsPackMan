package edu.ucsc.gameAI;

import edu.ucsc.gameAI.conditions.PacmanInRegion;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class Quadrant {
    int _x1,_x2,_y1,_y2;
    int _node;
    
    public Quadrant(int x1,int x2,int y1,int y2){
        _x1 = x1;
        _x2 = x2;
        _y1 = y1;
        _y2 = y2;
        
    }
    public boolean isPacmanQuadrant(Game game){
        PacmanInRegion pir = new PacmanInRegion(_x1, _x2, _y1, _y2);
        return pir.test(game);        
    }
    public boolean isGhostQuadrant(Game game, GHOST ghost){
        int index = game.getGhostCurrentNodeIndex(ghost);
        int x = game.getNodeXCood(index);
        int y = game.getNodeYCood(index);
        boolean insideX = (x <= _x1 && x >= _x2);
        boolean insideY = (y <= _y1 && y >= _y2);
        return (insideX && insideY);       
    }
    public void setWaypoint(int n){
        _node = n;
    }
    public int getWaypoint(){
        return _node;
    }
}
