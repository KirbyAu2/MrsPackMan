package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class LevelCount implements ICondition {
    
    private int _level;
    
    public LevelCount(int level)
    {
        _level = level;
    }
    
    public boolean test(Game game) 
    {
        return (game.getCurrentLevel() == _level);
    }
}
