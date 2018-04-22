package org.graphstream.demo.demo1;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class Demo1_2 {
	public static void main(String args[]) {
		System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("Tutorial 1");
        graph.display();

		graph.setAttribute("ui.quality");
		graph.setAttribute("ui.antialias");

		graph.addNode("A");
		graph.addNode("B");
		graph.addEdge("AB", "A", "B");
		graph.addNode("C");
		graph.addEdge("BC", "B", "C", true); // Directed edge.
		graph.addEdge("CA", "C", "A");


		Edge ab = graph.getEdge("AB");
		Edge bc = graph.getEdge("BC");
		Edge ca = graph.getEdge("CA");


        ab.setAttribute("ui.label", "AB");
		bc.setAttribute("ui.label", "BC");
		ca.setAttribute("ui.label", "CA");

		for(String id : new String[]{"A", "B", "C"}){
			graph.getNode(id).setAttribute("ui.label", id);
		}
		graph.setAttribute("ui.stylesheet", "url(data/style.css);");
	}
}