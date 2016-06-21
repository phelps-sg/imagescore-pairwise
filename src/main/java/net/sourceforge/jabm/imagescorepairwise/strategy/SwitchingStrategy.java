package net.sourceforge.jabm.imagescorepairwise.strategy;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import net.sourceforge.jabm.agent.Agent;
import net.sourceforge.jabm.imagescorepairwise.ImageScoreAgent;
import net.sourceforge.jabm.strategy.AbstractStrategy;
import net.sourceforge.jabm.strategy.Strategy;

public class SwitchingStrategy extends AbstractStrategy implements Cloneable, Serializable {

	protected int delay;
	
	protected int numberOfInteractions = 0;
	
	public void execute(List<Agent> otherAgents) {
		for(Agent other : otherAgents) {
			ImageScoreAgent otherAgent = (ImageScoreAgent) other;
			ImageScoreAgent thisAgent = (ImageScoreAgent) this.agent;
			if (numberOfInteractions < delay) {
				thisAgent.cooperate(otherAgent);
			} else {
				thisAgent.defect(otherAgent);
			}
		}
		numberOfInteractions++;
	}

	public Agent getAgent() {
		return agent;
	}
	
	public void setAgent(ImageScoreAgent agent) {
		this.agent = agent;
	}
	
	public Strategy transfer(Agent newAgent) {
		ImageScoreAgent imageScoreAgent = (ImageScoreAgent) newAgent;
		SwitchingStrategy result = new SwitchingStrategy();
		result.delay = this.delay;
		result.agent = this.agent;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SwitchingStrategy) {
			SwitchingStrategy other = (SwitchingStrategy) obj;
			return this.delay == other.delay;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return delay;
	}

	public int getDelay() {
		return delay;
	}

	@Required
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
}
