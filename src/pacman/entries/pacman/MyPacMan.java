package pacman.entries.pacman;

import edu.ucsc.gameAI.Collect;
import edu.ucsc.gameAI.IAction;
import edu.ucsc.gameAI.IdentifyClusters;
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
	private IdentifyClusters _ic;
	private int level;
	
	public MOVE getMove(Game game, long timeDue) 
	{
		//Place your game logic here to play the game as Ms Pac-Man
		if (this._ic == null || level != game.getCurrentLevel()){
			level = game.getCurrentLevel();
			this._ic = new IdentifyClusters(game);
		}
		//this._ic.colorClusters(game);
		//Collect goToNode = new Collect(game);
		//goToNode.makeDecision(game);
		//myMove = goToNode.getMove();
		//IdentifyClusters ic = new IdentifyClusters(game);
		if(game.wasPillEaten() || game.wasPowerPillEaten()){
			this._ic.pillEaten(game);
		}
		this._ic.makeDecision(game);
		myMove = this._ic.getMove();
		return myMove;
	}
}