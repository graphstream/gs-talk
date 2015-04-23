package org.graphstream.demo.tutorial1;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
public class Tutorial1 {
	public static void main(String args[]) {
		Graph graph = new SingleGraph("Tutorial 1");
		
		graph.display();
		
		graph.addNode("A");
		graph.addNode("B");
		graph.addEdge("AB", "A", "B");
		graph.addNode("C");
		graph.addEdge("BC", "B", "C", true); // Directed edge.
		graph.addEdge("CA", "C", "A");
	}
}