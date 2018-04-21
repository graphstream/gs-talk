package org.graphstream.tutorial.mobility;

import java.util.ArrayList;

import org.graphstream.graph.Graph;

public class MobilitySimulation {
	/** Set of mobile devices. */
	protected ArrayList<MobileDevice> devices;
	
	/** Number of mobile devices to create. */
	protected int deviceCount = 200;
	
	/** How many steps in the simulation. */
	protected int steps = 10000;
	
	/** The graph representation of the mobility model. */
	protected Graph graph;

	/** Initialize the simulation, and run it. */
	public MobilitySimulation() {
		addMobileDevices(deviceCount);
		initConnectedComponents();
		for(int step=0; step<steps; step++) {
			moveMobileDevices();
			checkConnections();
			showConnectedComponents();
			sleep();
		}
	}

	/** Create `count` mobile device elements and add them in the simulation. */
	protected void addMobileDevices(int count) {
		devices = new ArrayList<MobileDevice>();
		for(int device = 0; device < count; device++)
			devices.add(new MobileDevice(graph, String.format("%d", device)));
	}

	/** Move each mobile device. */
	protected void moveMobileDevices() {
		for(MobileDevice device: devices)
			device.next();
	}

	/** Check new connections and remove unreachable ones. */
	protected void checkConnections() {
		for( MobileDevice device: devices )
			device.checkConnections();		
	}

	/** A little nap. */
	protected void sleep() {
		try { Thread.sleep(100); } catch(InterruptedException e) {}
	}
	
	/** Initialize the connected components algorithm. */
	protected void initConnectedComponents() {
	}
	
	/** Update the connected components count. */
	protected void showConnectedComponents() {
	}
	
	public static void main(String args[]) {
		System.setProperty("org.graphstream.ui", "javafx");
		new MobilitySimulation();
	}
}
