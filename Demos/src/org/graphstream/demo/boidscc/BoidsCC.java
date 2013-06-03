package org.graphstream.demo.boidscc;

import java.io.FileInputStream;
import java.io.IOException;

import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.boids.BoidGraph;
import org.graphstream.boids.Probability;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

/**
 * Count the number of connected components in an evolving boids simulations where
 * boids are nodes and neighbors relations defines edges. 
 */
public class BoidsCC {
	public static void main(String args[]) {
		BoidGraph graph = new BoidGraph();

		// Load the boids configuration and configure the boids simulator.

		try {
			graph.loadDGSConfiguration(new FileInputStream("data/config.dgs"));
			graph.getSpecies("moustik").setReproductionProbability(new Probability.ConstantProbability(0));
			graph.getSpecies("moustik").setDeathCondition(new Probability.ConstantProbability(0));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create a connected components algorithm

		ConnectedComponents cc = new ConnectedComponents();
		cc.init(graph);
		graph.display(false);

		// Add a display of the CC count in the graph viewer.

		SpriteManager sm = new SpriteManager(graph);
		Sprite scc = sm.addSprite("CC");
		scc.setPosition(Units.PX, 20, 20, 0);
		scc.setAttribute("ui.label", "0");

		// Loop on the boids simulation and update the view.
		
		int steps = 0;
		
			while(steps < 10000) {
				graph.step();
			scc.setAttribute("ui.label", String.format("%d", cc.getConnectedComponentsCount()));
			steps ++;
			nap(20);
		}
	}
	
	public static void nap(long ms) {
		try { Thread.sleep(ms); } catch(InterruptedException e) { }
	}
}
