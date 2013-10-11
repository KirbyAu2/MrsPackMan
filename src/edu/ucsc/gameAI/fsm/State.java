package edu.ucsc.gameAI.fsm;

import java.util.Collection;

import edu.ucsc.gameAI.IAction;
import edu.ucsc.gameAI.fsm.IState;
import edu.ucsc.gameAI.fsm.ITransition;

/**
 * The interface for a state in a finite state machine. 
 * 
 * @author Josh McCoy
 */
public class State implements IState {
    private IAction _currentAction;
    private IAction _entryAction;
    private IAction _exitAction;
    private Collection<ITransition> _transitions;
    
    /**
     * 
     * @param entryAction sets the entry action that will be called on entry into this state
     * @param exitAction sets the exit action that will be called when leaving this state
     * @param transitions sets the transitions to call from this state to another
     */
    State(IAction entryAction, IAction exitAction, Collection<ITransition> transitions){
        _entryAction = entryAction;
        _exitAction = exitAction;
        _transitions = transitions;
    }
    
    /**
     * Determines the action appropriate for being in this state. 
     * @return Action for being in this state.
     */
    public IAction getAction(){
        //TO BE IMPLEMENTED
        //NOTE: Not sure if this is something that we set through the
        //constructor or if this is a set of conditionals?
        return _currentAction;
    }
    
    /**
     * Generates the action for entering this state.
     * @return The action associated with entering this state.
     */
    public IAction getEntryAction(){
        return _entryAction;
    }
    
    /**
     * Retrieves the action for exiting this state.
     * @return The action associated with exiting this state.
     */
    public IAction getExitAction(){
        return _exitAction;
    }
    
    /**
     * Accessor for all transitions out of this state.
     * @return The outbound transitions from this state.
     */
    public Collection<ITransition> getTransitions(){
        return _transitions;
    }
}

