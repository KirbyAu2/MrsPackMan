package edu.ucsc.gameAI.decisionTrees.binary;

import edu.ucsc.gameAI.IAction;

public class TreeAction implements IBinaryNode, IAction{
	public void doAction() {
		// Not really sure what to put here, Since this class should only ever be subclassed
		return;
	}
	
	public IAction makeDecision() {return this;}
}