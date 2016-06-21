package net.sourceforge.jabm.imagescorepairwise.report;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.sourceforge.jabm.agent.Agent;
import net.sourceforge.jabm.event.AbstractModel;
import net.sourceforge.jabm.event.SimEvent;
import net.sourceforge.jabm.event.GenerationStartingEvent;
import net.sourceforge.jabm.imagescorepairwise.event.AgentCooperatedEvent;
import net.sourceforge.jabm.report.Report;



public class RelationshipTracker extends AbstractModel implements
		 Report, Serializable {

	protected HashMap<Agent, HashMap<Agent, Double>> edges = 
		new HashMap<Agent, HashMap<Agent, Double>>();
	
	protected Collection<Agent> agents = new HashSet<Agent>();
	
	protected double maximumInvestment = Double.NEGATIVE_INFINITY;

	public RelationshipTracker() {
		super();
		
	}

	public void eventOccurred(SimEvent ev) {
		if (ev instanceof AgentCooperatedEvent) {
			final AgentCooperatedEvent event = (AgentCooperatedEvent) ev;
			Agent x = event.getAgent();
			Agent y = event.getRecipient();
			double amount = event.getInvestment();
			record(x, y, amount);
		} else if (ev instanceof GenerationStartingEvent) {
			edges.clear();
			agents = ((GenerationStartingEvent) ev).getSimulation().getSimulationController()
					.getPopulation().getAgents();
			maximumInvestment = Double.NEGATIVE_INFINITY;
		}
	}
	
	public void record(Agent x, Agent y, double amount) {
		assert x != null && y != null;
		HashMap<Agent,Double> m = edges.get(x);
		if (m == null) {
			m = new HashMap<Agent,Double>();
			edges.put(x, m);
		}
		Double current = m.get(y);
		if (current == null) {
			current = new Double(amount);			
		} else {
			current += amount;
		}
		m.put(y, current);
		if (current > maximumInvestment) {
			maximumInvestment = current;
		}
	}
	
	public double getTotalInvestment(Agent x, Agent y) {
		HashMap<Agent,Double> m = edges.get(x);
		if (m != null && m.get(y) != null) {
			return m.get(y);
		} else {
			return 0f;
		}
	}

	public Collection<Agent> getAgents() {
		return agents;
	}
	
	public double getMaximumInvestment() {
		return maximumInvestment;
	}

	public Map<Object, Number> getVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Object, Number> getVariableBindings() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
