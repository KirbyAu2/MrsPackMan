package edu.ucsc.gameAI.hfsm;

import java.util.Collection;

import pacman.game.Game;
import edu.ucsc.gameAI.IAction;

public class HFSM implements IHFSM {

    @Override
    public Collection<IHState> getStates() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setStates(Collection<IHState> states) {
        // TODO Auto-generated method stub

    }

    @Override
    public IAction getAction() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAction(IAction action) {
        // TODO Auto-generated method stub

    }

    @Override
    public IAction getEntryAction() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setEntryAction(IAction action) {
        // TODO Auto-generated method stub

    }

    @Override
    public IAction getExitAction() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setExitAction(IAction action) {
        // TODO Auto-generated method stub

    }

    @Override
    public Collection<IHTransition> getTransitions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTransitions(Collection<IHTransition> transitions) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addTransition(IHTransition transition) {
        // TODO Auto-generated method stub

    }

    @Override
    public IResult update(Game game) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IAction> updateDown(IHState state, int level, Game game) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setInitialState(IHState initialState) {
        // TODO Auto-generated method stub

    }

    @Override
    public IHState getInitialState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IHFSM getParent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setParent(IHFSM parent) {
        // TODO Auto-generated method stub

    }

}
