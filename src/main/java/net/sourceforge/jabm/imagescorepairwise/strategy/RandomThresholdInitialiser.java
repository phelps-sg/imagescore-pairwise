package net.sourceforge.jabm.imagescorepairwise.strategy;

import java.io.Serializable;
import java.util.Collection;

import cern.jet.random.AbstractDistribution;

import net.sourceforge.jabm.Population;
import net.sourceforge.jabm.agent.Agent;
import net.sourceforge.jabm.imagescorepairwise.ImageScoreAgent;
import net.sourceforge.jabm.init.AgentInitialiser;
import net.sourceforge.jabm.strategy.Strategy;

public class RandomThresholdInitialiser implements AgentInitialiser, Serializable {

	protected AbstractDistribution distribution;
	
	public RandomThresholdInitialiser(AbstractDistribution distribution) {
		super();
		this.distribution = distribution;
	}

	public void initialise(Population population) {
		for (Agent agent : population.getAgents()) {
			ImageScoreThresholdStrategy strategy = new ImageScoreThresholdStrategy(
					(ImageScoreAgent) agent, distribution.nextInt());
			agent.setStrategy(strategy);
			strategy.setAgent(agent);
		}
	}

}
