package net.sourceforge.jabm.imagescorepairwise.strategy;

import java.io.Serializable;
import java.util.List;

import net.sourceforge.jabm.agent.Agent;
import net.sourceforge.jabm.imagescorepairwise.ImageScoreAgent;
import net.sourceforge.jabm.strategy.AbstractStrategy;
import net.sourceforge.jabm.strategy.Strategy;

public class InteractionHistoryStrategy extends AbstractStrategy implements
		Cloneable, Serializable {

	protected float threshold = 1f;

	protected Strategy firstMoveStrategy;

	public InteractionHistoryStrategy(ImageScoreAgent agent,
			Strategy firstMoveStrategy, float threshold) {
		super(agent);
		this.threshold = threshold;
		this.firstMoveStrategy = firstMoveStrategy;
	}

	public InteractionHistoryStrategy(float threshold) {
		this(null, new ImageScoreThresholdStrategy(Math.round(threshold)),
				threshold);
	}
	
	public void execute(List<Agent> otherAgents) {
		ImageScoreAgent otherAgent = (ImageScoreAgent) otherAgents.get(0);
		float averageReciprocation = 0f;
		float totalReciprocation = 0f;
		ImageScoreAgent thisAgent = (ImageScoreAgent) agent;
		List<Double> history = thisAgent.getInteractionHistory().get(otherAgent);
		if (history != null) {
			for (Double x : history) {
				totalReciprocation += x;
			}
			averageReciprocation = totalReciprocation / history.size();
			if (averageReciprocation >= threshold) {
				thisAgent.cooperate(otherAgent);
			} else {
				thisAgent.defect(otherAgent);
			}
		} else {
			firstMoveStrategy.execute(otherAgents);
		}
	}

	public float getThreshold() {
		return threshold;
	}

	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

	public Strategy getFirstMoveStrategy() {
		return firstMoveStrategy;
	}

	public void setFirstMoveStrategy(Strategy firstMoveStrategy) {
		this.firstMoveStrategy = firstMoveStrategy;
	}

	public Strategy transfer(Agent newAgent) {
		return new InteractionHistoryStrategy((ImageScoreAgent) newAgent,
				firstMoveStrategy, threshold);
	}

	public boolean equals(Object obj) {
		if (obj instanceof InteractionHistoryStrategy) {
			return this.threshold == ((InteractionHistoryStrategy) obj).threshold;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int) threshold;
	}

	@Override
	public void setAgent(Agent agent) {
		super.setAgent(agent);
		firstMoveStrategy.setAgent(agent);
	}
//
//	@Override
//	protected Object clone() throws CloneNotSupportedException {
//		// TODO Auto-generated method stub
//		return super.clone();
//	}
	
	
}
