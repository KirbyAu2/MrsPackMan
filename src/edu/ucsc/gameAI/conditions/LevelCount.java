package src.edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class LevelCount implements ICondition {
    
    private Game _game;
    private int _level;
    
    public LevelCount(Game game, int level)
    {
        _game = game;
        _level = level;
    }
    
    public boolean test() 
    {
        return (_game.getCurrentLevel() == _level);
    }
}
