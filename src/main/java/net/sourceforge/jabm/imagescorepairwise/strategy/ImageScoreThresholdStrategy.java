package net.sourceforge.jabm.imagescorepairwise.strategy;

import java.io.Serializable;
import java.util.List;

import net.sourceforge.jabm.agent.Agent;
import net.sourceforge.jabm.imagescorepairwise.ImageScoreAgent;
import net.sourceforge.jabm.strategy.AbstractStrategy;
import net.sourceforge.jabm.strategy.Strategy;

public class ImageScoreThresholdStrategy extends AbstractStrategy implements Cloneable, Serializable {

	protected int threshold;
	
	protected Strategy firstMoveStrategy;
	
	public ImageScoreThresholdStrategy(ImageScoreAgent agent, int threshold) {
		super(agent);
		this.threshold = threshold;
	}
	
	public ImageScoreThresholdStrategy(int threshold) {
		super(null);
		this.threshold = threshold;
	}
	
	public void execute(List<Agent> otherAgents) {
		for(Agent other : otherAgents) {
			ImageScoreAgent otherAgent = (ImageScoreAgent) other;
			ImageScoreAgent thisAgent = (ImageScoreAgent) this.agent;
			if (!other.isInteracted() && firstMoveStrategy != null) {
				firstMoveStrategy.setAgent(this.agent);
				firstMoveStrategy.execute(otherAgents);
			} else {
				if (otherAgent.getImageScore() >= threshold ) {
					thisAgent.cooperate(otherAgent);
				} else {
					thisAgent.defect(otherAgent);
				}
			}
		}
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public Agent getAgent() {
		return agent;
	}
	
	public void setAgent(ImageScoreAgent agent) {
		this.agent = agent;
	}

//	@Override
//	public boolean equals(Object obj) {
//		ImageScoreThresholdStrategy other = (ImageScoreThresholdStrategy) obj;
//		return getThreshold() == other.getThreshold();
//	}
//
//	@Override
//	public int hashCode() {
//		return getThreshold();
//	}
	
	public Strategy transfer(Agent newAgent) {
		ImageScoreAgent imageScoreAgent = (ImageScoreAgent) newAgent;
		Strategy result = new ImageScoreThresholdStrategy(imageScoreAgent,
				threshold);
		return result;
	}
//
//	@Override
//	protected Object clone() throws CloneNotSupportedException {
//		// TODO Auto-generated method stub
//		return super.clone();
//	}

	public String toString() {
		return threshold + "";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ImageScoreThresholdStrategy) {
			ImageScoreThresholdStrategy other = (ImageScoreThresholdStrategy) obj;
			return this.threshold == other.threshold
					&& ((this.getFirstMoveStrategy() == null && 
							other.getFirstMoveStrategy() == null) ||
							this.getFirstMoveStrategy().equals(
									other.getFirstMoveStrategy()));
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return threshold;
	}

	public Strategy getFirstMoveStrategy() {
		return firstMoveStrategy;
	}

	public void setFirstMoveStrategy(Strategy firstMoveStrategy) {
		this.firstMoveStrategy = firstMoveStrategy;
	}
	
}
