package net.sourceforge.jabm.imagescorepairwise.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sourceforge.jabm.agent.Agent;
import net.sourceforge.jabm.Population;
import net.sourceforge.jabm.SimulationController;
import net.sourceforge.jabm.event.GenerationFinishedEvent;
import net.sourceforge.jabm.event.InteractionsFinishedEvent;
import net.sourceforge.jabm.event.SimEvent;
import net.sourceforge.jabm.event.SimulationStartingEvent;
import net.sourceforge.jabm.event.SimulationFinishedEvent;
import net.sourceforge.jabm.imagescorepairwise.ImageScoreAgent;
import net.sourceforge.jabm.report.Report;
import net.sourceforge.jabm.strategy.Strategy;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.jfree.data.xy.XYDataset;


public class InteractionHistoryLengthReport implements Report, Serializable {

	protected SummaryStatistics lengthStatistics = new SummaryStatistics();
	
	public void eventOccurred(SimEvent e) {		
		if (e instanceof SimulationFinishedEvent) {	
			lengthStatistics = new SummaryStatistics();
			SimulationFinishedEvent event = (SimulationFinishedEvent) e;
			SimulationController controller = 
				event.getSimulation().getSimulationController();
			Population population = controller.getPopulation();
			Iterator<Agent> agents = population.getAgentList().iterator();
			while (agents.hasNext()) {
				ImageScoreAgent agent = (ImageScoreAgent) agents.next();
				double value = agent.getInteractionHistory().size();
				lengthStatistics.addValue(value);
			}
		}		
	}
	
	public Map<Object,Number> getVariables() {
		HashMap<Object,Number> result = new HashMap<Object,Number>();
		result.put("historylength", lengthStatistics.getMean());
		return result;
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

