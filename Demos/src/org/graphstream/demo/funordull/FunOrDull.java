/**
 * 
 * Copyright (c) 2012 University of Le Havre
 * 
 * @file T2.java
 * @date Jul 3, 2012
 * 
 * @author Yoann Pigné
 * 
 */
package org.graphstream.demo.funordull;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * 
 */
public class FunOrDull {
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("org.graphstream.ui", "swing");
		Graph graph = new SingleGraph("fun or dull");
		graph.display();
		graph.setAttribute("ui.antialias");
		graph.setAttribute("stylesheet", "graph {padding : 50px;}"
				+ "node {size: 100px; fill-mode: image-scaled;}"
				+ "node.fun {fill-image: url('data/fun.gif');}"
				+ "node.dull {fill-image: url('data/dull.png');}");

		Node a = graph.addNode("A");
		Node b = graph.addNode("B");
		Node c = graph.addNode("C");
		graph.addEdge("AB", "A", "B");
		graph.addEdge("CB", "C", "B");
		graph.addEdge("AC", "A", "C");

		a.setAttribute("ui.class", "fun");
		b.setAttribute("ui.class", "fun");
		c.setAttribute("ui.class", "fun");
		while(true){
			Thread.sleep(1000);
			a.setAttribute("ui.class", "fun");
			c.setAttribute("ui.class", "dull");
			Thread.sleep(1000);
			b.setAttribute("ui.class", "dull");
			c.setAttribute("ui.class", "fun");
			Thread.sleep(1000);
			b.setAttribute("ui.class", "fun");
			a.setAttribute("ui.class", "dull");
				
		}

	}
}
