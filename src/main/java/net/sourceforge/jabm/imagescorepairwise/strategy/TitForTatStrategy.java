package net.sourceforge.jabm.imagescorepairwise.strategy;

import java.io.Serializable;
import java.util.List;

import net.sourceforge.jabm.agent.Agent;
import net.sourceforge.jabm.imagescorepairwise.ImageScoreAgent;
import net.sourceforge.jabm.strategy.AbstractStrategy;
import net.sourceforge.jabm.strategy.Strategy;

public class TitForTatStrategy extends AbstractStrategy implements Cloneable, Serializable {

	protected double threshold;
	
	public TitForTatStrategy(ImageScoreAgent agent, double threshold) {
		super(agent);
		this.threshold = threshold;
	}
	
	public TitForTatStrategy() {
		this(null, 1f);
	}
	
	public TitForTatStrategy(double threshold) {
		this(null, threshold);
	}

	public void execute(List<Agent> otherAgents) {
		ImageScoreAgent otherAgent = (ImageScoreAgent) otherAgents.get(0);
		Double lastReciprocation = null;
		ImageScoreAgent thisAgent = (ImageScoreAgent) agent;
		List<Double> history = thisAgent.getInteractionHistory().get(otherAgent);
		if (history != null) {			
			lastReciprocation = history.get(history.size()-1);
		}
		if (lastReciprocation == null) {
			thisAgent.cooperate(otherAgent);
		} else if (lastReciprocation > threshold) {
			thisAgent.cooperate(otherAgent);
		} else {
			thisAgent.defect(otherAgent);
		}
	}
//
//	public Strategy transfer(Agent newAgent) {
//		return new TitForTatStrategy((ImageScoreAgent) newAgent, threshold);
//	}
//
	public boolean equals(Object obj) {
		if (! (obj instanceof TitForTatStrategy)) {
			return false;
		}
		return this.threshold == ((TitForTatStrategy) obj).threshold;
	}

	public int hashCode() {
		return (int) threshold;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
	
}
