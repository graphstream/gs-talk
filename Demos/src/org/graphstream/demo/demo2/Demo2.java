package org.graphstream.demo.demo2;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDGS;

import java.io.IOException;

public class Demo2 {
	public static void main(String args[]) throws IOException, InterruptedException {
		System.setProperty("org.graphstream.ui", "swing");
		Graph graph = new SingleGraph("Demo2");
		graph.setAttribute("ui.antialias");
		graph.setAttribute("ui.quality");
		graph.setAttribute("ui.stylesheet", "url(data/style.css);");

		graph.display(false);
		FileSource source = new FileSourceDGS();
		source.addSink( graph );
		source.begin("data/demo2.dgs");
		while(source.nextStep()){
			Thread.sleep(2000);
		}
		source.end();
	}
}