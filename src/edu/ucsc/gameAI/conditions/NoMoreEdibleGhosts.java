package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class NoMoreEdibleGhosts implements ICondition {
            
    public NoMoreEdibleGhosts()
    {
        
    }
        
    public boolean test(Game game) 
    {
        Boolean noMore = true;
        for(GHOST ghost : GHOST.values()){
            if(game.isGhostEdible(ghost)){
                noMore = false;
                break;
            }
        }
        
        return noMore;
    }
}
