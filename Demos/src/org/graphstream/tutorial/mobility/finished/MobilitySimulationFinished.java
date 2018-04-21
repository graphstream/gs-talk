package org.graphstream.tutorial.mobility.finished;

import java.util.ArrayList;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.algorithm.ConnectedComponents;

public class MobilitySimulationFinished {
	/** Set of mobile devices. */
	protected ArrayList<MobileDeviceFinished> devices;
	
	/** Number of mobile devices to create. */
	protected int deviceCount = 200;
	
	/** How many steps in the simulation. */
	protected int steps = 10000;
	
	/** The graph representation of the mobility model. */
	protected Graph graph;
	
	/** Connected components display. */
	protected Sprite cc;
	
	/** The connected components algorithm. */
	protected ConnectedComponents ccalgo;

	/** Initialize the simulation, and run it. */
	public MobilitySimulationFinished() {
		graph = new SingleGraph("mobility model");
		graph.display(false);
		graph.setAttribute("ui.antialias");
		graph.setAttribute("ui.stylesheet", "edge { fill-color: grey; } sprite { size: 0px; }");
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
		devices = new ArrayList<MobileDeviceFinished>();
		for(int device = 0; device < count; device++)
			devices.add(new MobileDeviceFinished(graph, String.format("%d", device)));
	}

	/** Move each mobile device. */
	protected void moveMobileDevices() {
		for(MobileDeviceFinished device: devices)
			device.next();
	}

	/** Check new connections and remove unreachable ones. */
	protected void checkConnections() {
		for( MobileDeviceFinished device: devices )
			device.checkConnections();		
	}

	/** A little nap. */
	protected void sleep() {
		try { Thread.sleep(100); } catch(InterruptedException e) {}
	}
	
	/** Initialize the connected components algorithm. */
	protected void initConnectedComponents() {
		ccalgo = new ConnectedComponents();
		ccalgo.init(graph);
	
		SpriteManager sm = new SpriteManager(graph);
		cc = sm.addSprite("cc");
		cc.setPosition(Units.PX, 10, 10, 0);
	}
	
	/** Update the connected components count. */
	protected void showConnectedComponents() {
		cc.setAttribute("ui.label", "Connected components " + ccalgo.getConnectedComponentsCount());
	}
	
	public static void main(String args[]) {
		System.setProperty("org.graphstream.ui", "swing");
		new MobilitySimulationFinished();
	}
}
