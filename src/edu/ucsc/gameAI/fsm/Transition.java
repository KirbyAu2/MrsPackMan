package edu.ucsc.gameAI.fsm;

import edu.ucsc.gameAI.IAction;
import edu.ucsc.gameAI.ICondition;

/**
 * The interface for transitions in finite state machines.
 * 
 * @author Josh McCoy
 */
public class Transition implements ITransition {
    private IState _targetState;
    private IAction _action;
    private ICondition _condition;

    /**
     * 
     * @param targetState sets the aimed target state
     * @param action sets the action to be taken when this transition executes
     * @param condition sets the condition of when this transition is triggered
     */
    Transition(IState targetState, IAction action, ICondition condition){
        _targetState = targetState;
        _action = action;
        _condition = condition;
    }
    
    /**
     * Accesses the state that this transition leads to.
     * @return The state this transition leads to.
     */
    public IState getTargetState(){
        return _targetState;
    }

    /**
     * Generates the action associated with taking this transition.
     * @return The action associated with taking this transition.
     */
    public IAction getAction(){
        return _action;
    }
    
    /**
     * Sets the condition that determines if the transition is triggered.
     * @param condition A testable condition.
     */
    public void setCondition(ICondition condition){
        _condition = condition;
    }

    /**
     * Determines if this transition is triggered.
     * @return True if triggered, false if not.
     */
    public boolean isTriggered(){
        return _condition.test();
    }
}
