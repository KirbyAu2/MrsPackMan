package src.edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class Score implements ICondition {
    
    private Game _game;
    private int _min,_max;
    
    public Score(Game game, int min, int max)
    {
        _game = game;
        _min = min;
        _max = max;
    }
    
    public boolean test() 
    {
        int score =_game.getScore();
        return (score >= _min) && (score <= _max);
    }
}
