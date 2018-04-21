package org.graphstream.tutorial.modularity;

import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.ui.view.Viewer;

public class LinLogLayout {
    public static void main(String args[]) throws IOException, GraphParseException {
        System.setProperty("org.graphstream.ui", "javafx");
        (new LinLogLayout()).findCommunities("data/karate.gml");
    }

    private Graph graph;
    private Viewer viewer;

    public void findCommunities(String fileName) throws IOException, GraphParseException {
        graph = new SingleGraph("communities");
        viewer = graph.display(true);

        graph.read(fileName);
    }
}
