package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class NoPowerPillsLeft implements ICondition {
    private Boolean value;
    
    public NoPowerPillsLeft(Boolean bool)
    {
    	value = bool;
    }
    
    public boolean test(Game game) 
    {
        int[] pills = game.getActivePowerPillsIndices();
        if(pills.length > 0){
            return !value;
        }
        return value;
    }
}
