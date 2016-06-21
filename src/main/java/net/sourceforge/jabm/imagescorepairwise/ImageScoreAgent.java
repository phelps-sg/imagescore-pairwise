package net.sourceforge.jabm.imagescorepairwise;

import java.io.Serializable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import net.sourceforge.jabm.agent.AbstractAgent;
import net.sourceforge.jabm.agent.Agent;
import net.sourceforge.jabm.event.AgentArrivalEvent;
import net.sourceforge.jabm.event.RoundStartingEvent;
import net.sourceforge.jabm.imagescorepairwise.event.AgentCooperatedEvent;
import net.sourceforge.jabm.EventScheduler;
import net.sourceforge.jabm.strategy.Strategy;

public class ImageScoreAgent extends AbstractAgent implements Agent, Cloneable, Serializable {

	protected int imageScore;
	
	protected double fitness;
	
	protected double benefit;
	
	protected double cost;
	
	protected double minFitness;
	
	protected double initialFitness;
	
	protected int maxImageScore = 5;
	
	protected int minImageScore = -5;
	
	protected int initialImageScore = 0;
	
	protected int generation = 0;
	
	protected boolean alive = true;
	
	protected HashMap<Agent,List<Double>> interactionHistory;
	
	public ImageScoreAgent(Strategy strategy, EventScheduler scheduler) {
		super(scheduler);
		this.strategy = strategy;
		initialise();
//		assert listeners != null;
	}
	
	public ImageScoreAgent(EventScheduler scheduler) {
		this(null, scheduler);
	}
	
	public ImageScoreAgent() {
		this(null);
	}
	
	public void initialise() {
		setFitness(initialFitness);
		imageScore = initialImageScore;
		interacted = false;
		interactionHistory = new HashMap<Agent,List<Double>>();
	}
	
	public void cooperate(ImageScoreAgent other) {
		donate(other);
		fireEvent(new AgentCooperatedEvent(this, other, benefit));
		increaseImageScore();
	}
	
	public void defect(ImageScoreAgent other) {
		decreaseImageScore();
		other.receive(this, 0);
	}
	
	protected void increaseImageScore() {
		if (imageScore < maxImageScore) {
			imageScore++;
		}
	}
	
	protected void decreaseImageScore() {
		if (imageScore > minImageScore) {
			imageScore--;
		}
	}
	
	public void pay(double cost) {
		fitness -= cost;
	}
	
	public void pay() {
		pay(cost);
	}
	
	public void donate(ImageScoreAgent recipient) {
		recipient.receive(this, benefit);
		pay();
	}
	
	public void receive(ImageScoreAgent donor, double fitnessDelta) {
		fitness += fitnessDelta;
		List<Double> history = interactionHistory.get(donor);
		if (history == null) {
			history = new LinkedList<Double>();
			interactionHistory.put(donor, history);
		}
		history.add(fitnessDelta);
	}

	public int getImageScore() {
		return imageScore;
	}

	@Override
	public void onAgentArrival(AgentArrivalEvent event) {
		// Override superclass behaviour so that interacted is set to
		//  true _after_ strategy has been executed.
		assert alive;
		strategy.execute(event.getObjects());
		this.interacted = true;
		fitness += minFitness;
	}

	@Override
	public void onRoundStarting(RoundStartingEvent event) {
		// Override superclass behaviour to prevent interacted being set
		//  at the start of each tick
	}
	
	public void setImageScore(int imageScore) {
		this.imageScore = imageScore;
	}


	public double getPayoff() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public double getBenefit() {
		return benefit;
	}

	public void setBenefit(double benefit) {
		this.benefit = benefit;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public int getMaxImageScore() {
		return maxImageScore;
	}

	public void setMaxImageScore(int maxImageScore) {
		this.maxImageScore = maxImageScore;
	}

	public int getMinImageScore() {
		return minImageScore;
	}

	public void setMinImageScore(int minImageScore) {
		this.minImageScore = minImageScore;
	}

	public Object clone() throws CloneNotSupportedException {
		ImageScoreAgent result = (ImageScoreAgent) super.clone();
		return result;
	}

//	public Agent reproduce() {
//		try {
//			ImageScoreAgent result = (ImageScoreAgent) clone();
//			result.setStrategy(strategy.transfer(result));
//			result.generation++;
//			assert result.listeners != null;
//			return result;
//		} catch (CloneNotSupportedException e) {
//			throw new RuntimeException(e);
//		}
//	}

	public void kill() {
//		listeners = null;
		alive = false;
	}
	
	public int getGeneration() {
		return generation;
	}

	public String toString() {
		return "(" + getClass() + "@" + hashCode() + " generation:" + generation +  " fitness:" + fitness + " imageScore:" + imageScore + " strategy:" + strategy + ")";
	}

	public boolean isAlive() {
		return alive;
	}

	public double getMinFitness() {
		return minFitness;
	}

	public void setMinFitness(double minFitness) {
		this.minFitness = minFitness;
	}

	public int getInitialImageScore() {
		return initialImageScore;
	}

	public void setInitialImageScore(int initialImageScore) {
		this.initialImageScore = initialImageScore;
	}

	public HashMap<Agent, List<Double>> getInteractionHistory() {
		return interactionHistory;
	}

	public void setInteractionHistory(HashMap<Agent, List<Double>> interactionHistory) {
		this.interactionHistory = interactionHistory;
	}

	public double getInitialFitness() {
		return initialFitness;
	}

	public void setInitialFitness(double initialFitness) {
		this.initialFitness = initialFitness;
	}
//
//	public boolean isInteracted() {
//		return interacted;
//	}
//	
}
