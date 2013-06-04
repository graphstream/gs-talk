package org.graphstream.demo.dshp;

import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.networksimplex.DynamicOneToAllShortestPath;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class DynamicShortestPathDemo {
	
	public static void pause(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	public static void main(String[] args) {
		// create and display a graph
		System.setProperty("org.graphstream.ui.renderer",
				"org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Graph graph = new SingleGraph("test");
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.stylesheet", "url('data/style_shp.css')");
		graph.display();
		
		// initialize the algorithm
		DynamicOneToAllShortestPath algorithm = new DynamicOneToAllShortestPath(null);
		algorithm.init(graph);
		algorithm.setSource("0");
		algorithm.setAnimationDelay(200);
		
		// add some nodes and edges
		Generator generator = new DorogovtsevMendesGenerator();
		generator.addSink(graph);
		generator.begin();
		for (int i = 3; i <= 200; i++) {
			generator.nextEvents();
			algorithm.compute();
		}
		
		pause(2000);
		
		// now remove some nodes and edges
		for (int i = 200; i > 100; i--) {
			graph.removeNode(i);
			algorithm.compute();
		}
		
		pause(2000);
		
		// now change the source
		for (int i = 1; i <= 10; i++) {
			algorithm.setSource(i + "");
			algorithm.compute();
		}
		
	}

}
