package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class NoPowerPillsLeft implements ICondition {
        
    public NoPowerPillsLeft()
    {
    }
    
    public boolean test(Game game) 
    {
        int[] pills = game.getActivePowerPillsIndices();
        for(int i=0; i < pills.length; i++){
            if(game.isPowerPillStillAvailable(pills[i])){
                return false;
            }
        }
        return true;
    }
}
