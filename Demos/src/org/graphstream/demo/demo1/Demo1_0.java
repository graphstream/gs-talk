package org.graphstream.demo.demo1;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class Demo1_0 {
	public static void main(String args[]) {
		System.setProperty("org.graphstream.ui", "swing");

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