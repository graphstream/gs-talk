package org.graphstream.demo.tutorial2;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.stream.file.*;

public class Tutorial2 {
	public static void main(String args[]) {
		Graph graph = new SingleGraph("Tutorial2");	
		graph.display();
		graph.addAttribute("ui.antialias");
		try {
	        FileSource source = FileSourceFactory.sourceFor(
	        	"data/tutorial2.dgs");
	        source.addSink( graph );
	        source.begin("data/tutorial2.dgs");
	        while( source.nextEvents() );
	        source.end();
        } catch( Exception e ) { e.printStackTrace(); }
	}
}