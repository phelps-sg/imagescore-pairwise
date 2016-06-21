package net.sourceforge.jabm.imagescorepairwise.event;

import java.io.Serializable;

import net.sourceforge.jabm.agent.AbstractAgent;
import net.sourceforge.jabm.agent.Agent;
import net.sourceforge.jabm.event.AgentEvent;

public class AgentCooperatedEvent extends AgentEvent implements Serializable {

	protected AbstractAgent recipient;
	
	protected double investment;
	
	public AgentCooperatedEvent(Agent agent, AbstractAgent donar,
			double investment) {
		super(agent);
		this.recipient = donar;
		this.investment = investment;
	}

	public AbstractAgent getRecipient() {
		return recipient;
	}

	public double getInvestment() {
		return investment;
	}
	
	
	
	
}
