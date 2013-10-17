package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class MazeIndex implements ICondition {
    
    private int _index;
    
    public MazeIndex(int index)
    {
        _index = index;
    }
    
    public boolean test(Game game) 
    {
        return (game.getMazeIndex() == _index);
    }
}
