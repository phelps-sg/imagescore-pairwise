package net.sourceforge.jabm.imagescorepairwise.event;

import java.io.Serializable;

import net.sourceforge.jabm.event.SimEvent;
import net.sourceforge.jabm.imagescorepairwise.report.RelationshipTracker;

public class RelationshipGraphChangedEvent extends SimEvent implements Serializable {

	protected RelationshipTracker relationshipTracker;

	public RelationshipGraphChangedEvent(RelationshipTracker relationshipTracker) {
		super();
		this.relationshipTracker = relationshipTracker;
	}
	
}
