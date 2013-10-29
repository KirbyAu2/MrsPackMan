package pacman.entries.ghosts;

import java.util.EnumMap;
import java.util.LinkedList;

import pacman.controllers.Controller;
import edu.ucsc.gameAI.fsm.IState;
import edu.ucsc.gameAI.fsm.ITransition;
import edu.ucsc.gameAI.fsm.State;
import edu.ucsc.gameAI.fsm.Transition;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import edu.ucsc.gameAI.conditions.IsEdible;
import edu.ucsc.gameAI.conditions.PacmanInRegion;
import edu.ucsc.gameAI.conditions.PacmanIsDangerous;
import edu.ucsc.gameAI.conditions.PowerPillWasEaten;
import edu.ucsc.gameAI.conditions.SafeToPursue;
import edu.ucsc.gameAI.decisionTrees.binary.*;
import edu.ucsc.gameAI.fsm.StateMachine;
import edu.ucsc.gameAI.*;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getActions() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.ghosts.mypackage).
 */
public class MyGhosts extends Controller<EnumMap<GHOST, MOVE>> {
    private StateMachine fsm;
    private StateMachine fsmBlinky;
    private StateMachine fsmPinky;
    private StateMachine fsmInky;
    private StateMachine fsmSue;
    // private LinkedList<StateMachine> fsms;
    private StateMachine[] fsms;
    private State pursueState;
    private State evadeState;
    private Transition transEdible;
    private LinkedList<ITransition> listEdible;
    private Transition transPursuit;
    private LinkedList<ITransition> listPursuit;
    private int currentGhost = 0;

    private EnumMap<GHOST, MOVE> myMoves = new EnumMap<GHOST, MOVE>(GHOST.class);
    private State pursueStateBlinky;
    private Transition transEdibleBlinky;
    private LinkedList<ITransition> listEdibleBlinky;
    private State evadeStateBlinky;
    private Transition transPursuitBlinky;
    private LinkedList<ITransition> listPursuitBlinky;
    private State pursueStatePinky;
    private Transition transEdiblePinky;
    private State evadeStatePinky;
    private LinkedList<ITransition> listEdiblePinky;
    private Transition transPursuitPinky;
    private LinkedList<ITransition> listPursuitPinky;
    private State pursueStateInky;
    private Transition transEdibleInky;
    private LinkedList<ITransition> listEdibleInky;
    private State evadeStateInky;
    private Transition transPursuitInky;
    private LinkedList<ITransition> listPursuitInky;
    private State pursueStateSue;
    private Transition transEdibleSue;
    private State evadeStateSue;
    private LinkedList<ITransition> listEdibleSue;
    private Transition transPursuitSue;
    private LinkedList<ITransition> listPursuitSue;

    public MyGhosts() {
        // fsms = new LinkedList<StateMachine>();
        fsms = new StateMachine[4];
        // add an fsm for each ghost
        // fsms.add(fsmBlinky);
        // //////////////////////////////////////////////////////
        // fsms.add(fsmPinky);

        // fsms.add(fsmInky);

        // fsms.add(fsmSue);
    }

    public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
        myMoves.clear();

        for (GHOST ghost : GHOST.values()) // for each ghost
        {
            /*
             * BinaryDecision edibleBinaryDecision = new BinaryDecision();
             * edibleBinaryDecision.setCondition(new IsEdible(ghost));
             * edibleBinaryDecision.setTrueBranch(new GoLeftAction());
             * edibleBinaryDecision.setFalseBranch(new GoRightAction());
             */
            // check if fsm is built for each ghost
            // if not, then built an fsm for the ghost
            if (fsmBlinky == null) {
                buildFSMBlinky(game, ghost);
            }
            if (fsmPinky == null) {
                buildFSMPinky(game, ghost);
            }
            if (fsmInky == null) {
                buildFSMInky(game, ghost);
            }
            if (fsmSue == null) {
                buildFSMSue(game, ghost);
            }
            /*
             * fsm = new StateMachine(); // Ghost are either in pursuit of
             * pacman or evading after powerpill is eaten
             * 
             * // Set up state to pursue target pursueState = new State(); ////
             * Move towards Pacman pursueState.setAction(new
             * GhostMoveTowardsNode(game, ghost,
             * game.getPacmanCurrentNodeIndex())); //// Transition to evade
             * state if powerpill was eaten transEdible = new Transition();
             * transEdible.setCondition(new PacmanIsDangerous(ghost));
             * transEdible.setTargetState(evadeState); //// Add the transition
             * to the pursue state listEdible = new LinkedList<ITransition>();
             * listEdible.add(transEdible);
             * pursueState.setTransitions(listEdible);
             * 
             * // Set up state to evade pacman evadeState = new State();
             * evadeState.setAction(new EvadePacmanMove(game, ghost));
             * transPursuit = new Transition(); transPursuit.setCondition(new
             * SafeToPursue(ghost)); transPursuit.setTargetState(pursueState);
             * listPursuit = new LinkedList<ITransition>();
             * listPursuit.add(transPursuit);
             * evadeState.setTransitions(listPursuit);
             * 
             * fsm.setCurrentState(evadeState);
             */
            if (game.doesGhostRequireAction(ghost)) // if ghost requires an
                                                    // action
            {
                // System.out.println("inside ghost requires move code block");
                switch ((GHOST) ghost) {
                case BLINKY:
                    currentGhost = 0;
                    // System.out.println("find move for Blinky");
                    if (fsmBlinky.getCurrentState() == pursueStateBlinky) {
                        System.out.println("Blinky in pursue mode");
                    } else if (fsmBlinky.getCurrentState() == evadeStateBlinky) {
                        System.out.println("Blinky in evade mode");
                    } else {
                        System.out.println("Blinky in neither mode");
                    }
                    pursueStateBlinky.setAction(new GhostMoveTowardsNode(game,
                            ghost, game.getPacmanCurrentNodeIndex()));
                    // // Transition to evade state if powerpill was eaten
                    transEdibleBlinky
                            .setCondition(new PacmanIsDangerous(ghost));
                    // Set up state to evade pacman
                    evadeStateBlinky
                            .setAction(new EvadePacmanMove(game, ghost));
                    transPursuitBlinky.setCondition(new SafeToPursue(ghost));
                    break;
                case PINKY:
                    currentGhost = 1;
                    // System.out.println("find move for Pinky");
                    if (fsmPinky.getCurrentState() == pursueStatePinky) {
                        System.out.println("Pinky in pursue mode");
                    } else if (fsmPinky.getCurrentState() == evadeStatePinky) {
                        System.out.println("Pinky in evade mode");
                    } else {
                        System.out.println("Pinky in neither mode");
                    }
                    pursueStatePinky.setAction(new GhostMoveTowardsNode(game,
                            ghost, game.getPacmanCurrentNodeIndex()));
                    // // Transition to evade state if powerpill was eaten
                    transEdiblePinky.setCondition(new PacmanIsDangerous(ghost));
                    // Set up state to evade pacman
                    evadeStatePinky.setAction(new EvadePacmanMove(game, ghost));
                    transPursuitPinky.setCondition(new SafeToPursue(ghost));
                    break;
                case INKY:
                    currentGhost = 2;
                    // System.out.println("find move for Inky");
                    if (fsmInky.getCurrentState() == pursueStateInky) {
                        System.out.println("Inky in pursue mode");
                    } else if (fsmInky.getCurrentState() == evadeStateInky) {
                        System.out.println("Inky in evade mode");
                    } else {
                        System.out.println("Inky in neither mode");
                    }
                    pursueStateInky.setAction(new GhostMoveTowardsNode(game,
                            ghost, game.getPacmanCurrentNodeIndex()));
                    // // Transition to evade state if powerpill was eaten
                    transEdibleInky.setCondition(new PacmanIsDangerous(ghost));
                    // Set up state to evade pacman
                    evadeStateInky.setAction(new EvadePacmanMove(game, ghost));
                    transPursuitInky.setCondition(new SafeToPursue(ghost));
                    break;
                case SUE:
                    currentGhost = 3;
                    // System.out.println("find move for Sue");
                    if (fsmSue.getCurrentState() == pursueStateSue) {
                        System.out.println("Sue in pursue mode");
                    } else if (fsmSue.getCurrentState() == evadeStateSue) {
                        System.out.println("Sue in evade mode");
                    } else {
                        System.out.println("Sue in neither mode");
                    }
                    pursueStateSue.setAction(new GhostMoveTowardsNode(game,
                            ghost, game.getPacmanCurrentNodeIndex()));
                    // // Transition to evade state if powerpill was eaten
                    transEdibleSue.setCondition(new PacmanIsDangerous(ghost));
                    // Set up state to evade pacman
                    evadeStateSue.setAction(new EvadePacmanMove(game, ghost));
                    transPursuitSue.setCondition(new SafeToPursue(ghost));
                    fsms[currentGhost].update(game);
                    /*
                     * if (fsms[currentGhost].getCurrentState() ==
                     * pursueStateSue) { System.out.println("ghost " +
                     * currentGhost + " in pursue mode"); GhostMoveTowardsNode
                     * ghostMove = new GhostMoveTowardsNode( game, ghost,
                     * game.getPacmanCurrentNodeIndex()); myMoves.put(ghost,
                     * fsms[currentGhost].getCurrentState()
                     * .getAction().getMove()); } else if
                     * (fsms[currentGhost].getCurrentState() == evadeState) {
                     * System.out.println("ghost " + currentGhost +
                     * " in evade mode"); EvadePacmanMove ghostMove = new
                     * EvadePacmanMove(game, ghost); myMoves.put(ghost,
                     * fsms[currentGhost].getCurrentState()
                     * .getAction().getMove()); }
                     */
                    break;
                }
                fsms[currentGhost].update(game);
                // fsm.update(game);
                // System.out.println(fsms[currentGhost].getCurrentState().toString());
                if (fsms[currentGhost].getCurrentState() == null) {
                    System.out.println("current State is null");
                }
                if ((fsms[currentGhost].getCurrentState() == pursueStateBlinky)
                        || (fsms[currentGhost].getCurrentState() == pursueStatePinky)
                        || (fsms[currentGhost].getCurrentState() == pursueStateInky)
                        || (fsms[currentGhost].getCurrentState() == pursueStateSue)) {
                    System.out.println("ghost " + currentGhost
                            + " in pursue mode");
                    GhostMoveTowardsNode ghostMove = new GhostMoveTowardsNode(
                            game, ghost, game.getPacmanCurrentNodeIndex());
                    //fsms[currentGhost].getCurrentState().getAction().
                    myMoves.put(ghost, fsms[currentGhost].getCurrentState()
                            .getAction().getMove(game));
                    // myMoves.put(ghost,
                    // ghostMove.makeDecision(game).getMove());
                    // System.out.println("in Pursue State");
                } else if ((fsms[currentGhost].getCurrentState() == evadeStateBlinky)
                        || (fsms[currentGhost].getCurrentState() == evadeStatePinky)
                        || (fsms[currentGhost].getCurrentState() == evadeStateInky)
                        || (fsms[currentGhost].getCurrentState() == evadeStateSue)) {
                    System.out.println("ghost " + currentGhost
                            + " in evade mode");
                    EvadePacmanMove ghostMove = new EvadePacmanMove(game, ghost);
                    myMoves.put(ghost, fsms[currentGhost].getCurrentState()
                            .getAction().getMove(game));
                     //myMoves.put(ghost,
                     //ghostMove.makeDecision(game).getMove());
                     System.out.println("in Evade State");
                }
                // myMoves.put(ghost,
                // fsm.getCurrentState().getAction().getMove());
                // fsm.getCurrentState().getAction().getMove();
                // myMoves.put(ghost,
                // fsm.getCurrentState().getAction().getMove());

                // if (edibleBinaryDecision.makeDecision(game).getClass() ==
                // GoLeftAction.class)
                // myMoves.put(ghost,MOVE.LEFT);
                // else
                // myMoves.put(ghost,MOVE.RIGHT);
            }
        }

        return myMoves;
    }

    private void buildFSMBlinky(Game game, GHOST ghost) {
        fsmBlinky = new StateMachine();
        // Ghost are either in pursuit of pacman or evading after powerpill is
        // eaten
        // Set up state to pursue target
        pursueStateBlinky = new State();
        evadeStateBlinky = new State();
        transEdibleBlinky = new Transition();
        transPursuitBlinky = new Transition();
        listEdibleBlinky = new LinkedList<ITransition>();
        listPursuitBlinky = new LinkedList<ITransition>();
        // // Move towards Pacman
        pursueStateBlinky.setAction(new GhostMoveTowardsNode(game, ghost, game
                .getPacmanCurrentNodeIndex()));
        // // Transition to evade state if powerpill was eaten
        transEdibleBlinky.setCondition(new PacmanIsDangerous(ghost));
        transEdibleBlinky.setTargetState(evadeStateBlinky);
        // // Add the transition to the pursue state
        listEdibleBlinky.add(transEdibleBlinky);
        pursueStateBlinky.setTransitions(listEdibleBlinky);
        // Set up state to evade pacman
        evadeStateBlinky.setAction(new EvadePacmanMove(game, ghost));
        transPursuitBlinky.setCondition(new SafeToPursue(ghost));
        transPursuitBlinky.setTargetState(pursueStateBlinky);
        listPursuitBlinky.add(transPursuitBlinky);
        evadeStateBlinky.setTransitions(listPursuitBlinky);
        // Set initial state
        fsmBlinky.setCurrentState(pursueStateBlinky);
        fsms[0] = fsmBlinky;
    }

    private void buildFSMSue(Game game, GHOST ghost) {
        fsmSue = new StateMachine();
        // Ghost are either in pursuit of pacman or evading after powerpill is
        // eaten
        // Set up state to pursue target
        pursueStateSue = new State();
        evadeStateSue = new State();
        transEdibleSue = new Transition();
        transPursuitSue = new Transition();
        listEdibleSue = new LinkedList<ITransition>();
        listPursuitSue = new LinkedList<ITransition>();
        // // Move towards Pacman
        pursueStateSue.setAction(new GhostMoveTowardsNode(game, ghost, game
                .getPacmanCurrentNodeIndex()));
        // // Transition to evade state if powerpill was eaten
        transEdibleSue.setCondition(new PacmanIsDangerous(ghost));
        transEdibleSue.setTargetState(evadeStateSue);
        // // Add the transition to the pursue state
        listEdibleSue.add(transEdibleSue);
        pursueStateSue.setTransitions(listEdibleSue);
        // Set up state to evade pacman
        evadeStateSue.setAction(new EvadePacmanMove(game, ghost));
        transPursuitSue.setCondition(new SafeToPursue(ghost));
        transPursuitSue.setTargetState(pursueStateSue);
        listPursuitSue.add(transPursuitSue);
        evadeStateSue.setTransitions(listPursuitSue);
        // Set initial state
        fsmSue.setCurrentState(pursueStateSue);
        fsms[3] = fsmSue;
    }

    private void buildFSMInky(Game game, GHOST ghost) {
        fsmInky = new StateMachine();
        // Ghost are either in pursuit of pacman or evading after powerpill is
        // eaten
        // Set up state to pursue target
        pursueStateInky = new State();
        evadeStateInky = new State();
        transEdibleInky = new Transition();
        transPursuitInky = new Transition();
        listEdibleInky = new LinkedList<ITransition>();
        listPursuitInky = new LinkedList<ITransition>();
        // // Move towards Pacman
        pursueStateInky.setAction(new GhostMoveTowardsNode(game, ghost, game
                .getPacmanCurrentNodeIndex()));
        // // Transition to evade state if powerpill was eaten
        transEdibleInky.setCondition(new PacmanIsDangerous(ghost));
        transEdibleInky.setTargetState(evadeStateInky);
        // // Add the transition to the pursue state
        listEdibleInky.add(transEdibleInky);
        pursueStateInky.setTransitions(listEdibleInky);
        // Set up state to evade pacman
        evadeStateInky.setAction(new EvadePacmanMove(game, ghost));
        transPursuitInky.setCondition(new SafeToPursue(ghost));
        transPursuitInky.setTargetState(pursueStateInky);
        listPursuitInky.add(transPursuitInky);
        evadeStateInky.setTransitions(listPursuitInky);
        // Set initial state
        fsmInky.setCurrentState(pursueStateInky);
        fsms[2] = fsmInky;
    }

    private void buildFSMPinky(Game game, GHOST ghost) {
        fsmPinky = new StateMachine();
        // Ghost are either in pursuit of pacman or evading after powerpill is
        // eaten
        // Set up state to pursue target
        pursueStatePinky = new State();
        evadeStatePinky = new State();
        transEdiblePinky = new Transition();
        transPursuitPinky = new Transition();
        listEdiblePinky = new LinkedList<ITransition>();
        listPursuitPinky = new LinkedList<ITransition>();
        // // Move towards Pacman
        pursueStatePinky.setAction(new GhostMoveTowardsNode(game, ghost, game
                .getPacmanCurrentNodeIndex()));
        // // Transition to evade state if powerpill was eaten;
        transEdiblePinky.setCondition(new PacmanIsDangerous(ghost));
        transEdiblePinky.setTargetState(evadeStatePinky);
        // // Add the transition to the pursue state
        listEdiblePinky.add(transEdiblePinky);
        pursueStatePinky.setTransitions(listEdiblePinky);

        // Set up state to evade pacman
        evadeStatePinky.setAction(new EvadePacmanMove(game, ghost));

        transPursuitPinky.setCondition(new SafeToPursue(ghost));
        transPursuitPinky.setTargetState(pursueStatePinky);

        listPursuitPinky.add(transPursuitPinky);
        evadeStatePinky.setTransitions(listPursuitPinky);
        // Set initial state
        fsmPinky.setCurrentState(pursueStatePinky);
        fsms[1] = fsmPinky;
    }
}