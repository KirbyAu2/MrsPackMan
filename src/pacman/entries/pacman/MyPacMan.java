package pacman.entries.pacman;

import java.util.ArrayList;
import java.util.Collection;

import edu.ucsc.gameAI.Collect;
import edu.ucsc.gameAI.IAction;
import edu.ucsc.gameAI.IdentifyClusters;
import edu.ucsc.gameAI.MoveTowardsNode;
import edu.ucsc.gameAI.conditions.GhostNearPacman;
import edu.ucsc.gameAI.decisionTrees.binary.BinaryDecision;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import edu.ucsc.gameAI.fsm.ITransition;
import edu.ucsc.gameAI.fsm.StateMachine;
import edu.ucsc.gameAI.fsm.State;
import edu.ucsc.gameAI.fsm.Transition;


/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE>
{
	private MOVE myMove=MOVE.NEUTRAL;
	private BinaryDecision _tree;
	private IdentifyClusters _ic;
	private StateMachine _stateMachine;
	private State _seekClusters;
	private State _run;
	private State _powerPillTime;
	
	public MyPacMan(){
	    _stateMachine = new StateMachine();
	    _seekClusters = new State();
	    _run = new State();
	    _powerPillTime = new State();
	    _ic = new IdentifyClusters();
	    
	    //Seek Clusters State
	    _seekClusters.setAction(_ic);
	    Transition scTransition = new Transition();
	    scTransition.setCondition(new GhostNearPacman(5));
	    scTransition.setTargetState(_seekClusters);
	    Collection<ITransition> scTransCollection = new ArrayList<ITransition>();
	    scTransCollection.add(scTransition);
	    _seekClusters.setTransitions(scTransCollection);
	    
	    _stateMachine.setCurrentState(_seekClusters);
	    
	    //Run state
	    
	    
	    
	    //Power Pill Near!
	    
	    
	    //Eat Ghosts
	    
	}
	
	public MOVE getMove(Game game, long timeDue) 
	{
	    Collection<IAction> actions = _stateMachine.update(game);
	    System.out.println(actions);
	    for(IAction a : actions){
	        myMove = a.getMove(game);
	    }
	    
		return myMove;
	}
}