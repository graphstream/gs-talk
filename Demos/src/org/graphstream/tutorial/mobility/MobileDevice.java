package org.graphstream.tutorial.mobility;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class MobileDevice {
	/** Position of the device. */
	protected double x, y;
	
	/** Where to go ? */
	protected double targetx, targety;
	
	/** Constant speed. */
	protected double speed;
	
	/** How many time to wait before moving. */
	protected int pause;
	
	/** The global graph. */
	protected Graph graph;
	
	/** Build a new mobile device */
	public MobileDevice(Graph graph, String name) {
		this.x = Math.random();
		this.y = Math.random();
		nextTarget();
	}
	
	/** Next action. */
	public void next() {
		if (pause > 0) {
			pause--;			// Stand still.
		} else if(atTarget()) {
			nextTarget();		// Choose a next target, speed, and wait.
		} else {
			move();				// Move a little toward the target.
		}
	}
	
	/** Is the device close enough to another to be connected ? */
	protected boolean closeTo(Node other) {
		return false;
	}
	
	/** Change the target destination of the device. */
	protected void nextTarget() {
		targetx = Math.random();			// New random destination.
		targety = Math.random();
		speed   = 0.02+Math.random()*0.03f;	// New random speed.
		pause   = (int)(Math.random()*10);	// New random pause time at
	}										// target.
	
	/** Move the device toward its target. */
	protected void move() {
		x += (targetx-x)*speed;		// Move a little (slow down at arrival).
		y += (targety-y)*speed;
	}
	
	/** Have we reached the target ? */
	protected boolean atTarget() {
		return (Math.abs(targetx-x) < 0.1 && Math.abs(targety-y) < 0.01);
	}
	
	/** Check the connections with other devices, create or delete them. */
	protected void checkConnections() {
		removeConnections();
		createConnections();
	}

	/** Check any disappearing connection. */
	protected void removeConnections() {
	}
	
	/** Check any appearing connection. */
	protected void createConnections() {
	}
}
