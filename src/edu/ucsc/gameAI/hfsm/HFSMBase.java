package edu.ucsc.gameAI.hfsm;

import pacman.game.Game;
import edu.ucsc.gameAI.IAction;

public class HFSMBase implements IHFSMBase {
	IAction _currentAction;
	IHTransition _transition;
	int _level;
	
	HFSMBase(){
	    
	}
	
    public IAction getAction(){
        return _currentAction;
    }
    
    public void setAction(IAction action){
        _currentAction = action;
    }
    
    public IResult update(Game game){
        IResult result = new Result();
        
        if(_currentAction != null){
            result.addAction(_currentAction);
        }
        if(_transition != null){
            result.setTransition(_transition);
        }
        result.setLevel(_level);
        
        return result;
    }
}
