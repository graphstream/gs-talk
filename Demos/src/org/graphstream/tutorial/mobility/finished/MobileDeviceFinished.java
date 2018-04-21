package org.graphstream.tutorial.mobility.finished;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.*;

public class MobileDeviceFinished {
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
	
	/** The node of the graph representing the device. */
	protected Node node;
	
	/** Build a new mobile device */
	public MobileDeviceFinished(Graph graph, String name) {
		this.graph = graph;	
		this.x = Math.random();
		this.y = Math.random();
		nextTarget();
		node = graph.addNode(name);// Associate the device with a node.	
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
		double otherxy[] = nodePosition(other);		
		return(Math.abs(x-otherxy[0]) < 0.07 && Math.abs(y-otherxy[1]) < 0.07);
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
		node.setAttribute("x", x);	// Modify the node attributes to move
		node.setAttribute("y", y);	// it in the graph viewer.
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
		ArrayList<Edge> toRemove = new ArrayList<>();
		node.edges()
				.filter(edge -> !closeTo(edge.getOpposite(node)))
				.collect(Collectors.toList())
				.forEach(e -> graph.removeEdge(e));
	}
	
	/** Check any appearing connection. */
	protected void createConnections() {
		for(Node other: graph) {
			if(other != node && closeTo(other)) {
				if(!node.hasEdgeToward(other.getId())) {
					graph.addEdge(
							String.format("%s-%s", node.getId(), other.getId()),
							node.getId(), other.getId());
				}
			}
		}		
	}
}
