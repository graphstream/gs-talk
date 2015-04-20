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
		System.setProperty("org.graphstream.ui.renderer",
				"org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Graph graph = new SingleGraph("fun or dull");
		graph.display();
		graph.addAttribute("ui.antialias");
		graph.addAttribute("stylesheet", "graph {padding : 50px;}"
				+ "node {size: 100px; fill-mode: image-scaled;}"
				+ "node.fun {fill-image: url('data/fun.gif');}"
				+ "node.dull {fill-image: url('data/dull.png');}");

		Node a = graph.addNode("A");
		Node b = graph.addNode("B");
		Node c = graph.addNode("C");
		graph.addEdge("AB", "A", "B");
		graph.addEdge("CB", "C", "B");
		graph.addEdge("AC", "A", "C");

		a.addAttribute("ui.class", "fun");
		b.addAttribute("ui.class", "fun");
		c.addAttribute("ui.class", "fun");
		while(true){
			Thread.sleep(1000);
			a.addAttribute("ui.class", "fun");
			c.addAttribute("ui.class", "dull");
			Thread.sleep(1000);
			b.addAttribute("ui.class", "dull");
			c.addAttribute("ui.class", "fun");
			Thread.sleep(1000);
			b.addAttribute("ui.class", "fun");
			a.addAttribute("ui.class", "dull");
				
		}

	}
}
