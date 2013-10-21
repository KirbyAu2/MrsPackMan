package pacman.entries.pacman;

import edu.ucsc.gameAI.Collect;
import edu.ucsc.gameAI.IAction;
import edu.ucsc.gameAI.MoveTowardsNode;
import edu.ucsc.gameAI.decisionTrees.binary.BinaryDecision;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE>
{
	private MOVE myMove=MOVE.NEUTRAL;
	private BinaryDecision _tree;
	
	
	public MOVE getMove(Game game, long timeDue) 
	{
		//Place your game logic here to play the game as Ms Pac-Man
		Collect goToNode = new Collect(game);
		goToNode.makeDecision(game);
		myMove = goToNode.getMove();
		return myMove;
	}
}