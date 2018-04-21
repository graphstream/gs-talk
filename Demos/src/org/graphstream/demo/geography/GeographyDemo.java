package org.graphstream.demo.geography;

import org.graphstream.algorithm.measure.AbstractCentrality;
import org.graphstream.geography.ElementDescriptor;
import org.graphstream.geography.osm.GeoSourceOSM_RoadNetwork;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GeographyDemo {

    String osmFile = "";

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("road network");
        graph.setAttribute("ui.stylesheet", "url('data/geography.css')");
        graph.setAttribute("ui.antialias", true);



        GeoSourceOSM_RoadNetwork src = new GeoSourceOSM_RoadNetwork("data/Poznan2_filtered.osm.xml");
        src.addSink(graph);

        src.read(); src.end();


        removeMiddleNodes(graph);

        computeWeights(graph);

        graph.display(false);



        GeoProjectionPipe pipe = new GeoProjectionPipe();
        pipe.setLon0Lat0(16.8580, 52.3713);
        pipe.addSink(graph);
        graph.addSink(pipe);
        graph.nodes().forEach(node -> {
            node.setAttribute("lonlat", node.getNumber("x"), node.getNumber("y"));

        });


        BetweennessCentrality betweenness = new BetweennessCentrality("length", "betweenness", AbstractCentrality.NormalizationMode.MAX_1_MIN_0);
        betweenness.init(graph);
        betweenness.compute();


        graph.nodes().forEach(node -> node.leavingEdges().forEach(edge -> edge.setAttribute("ui.color", (float)node.getNumber("betweenness"))));

    }

    private static void computeWeights(Graph graph) {
        graph.edges().forEach(edge -> {
            edge.setAttribute("length",GraphPosLengthUtils.edgeLength(edge));
        });
    }

    private static void removeMiddleNodes(Graph graph) {
        System.out.println("nodes: "+graph.getNodeCount()+" edges: "+graph.getEdgeCount()+ " 2 neighbors: "+graph.nodes().mapToInt((node)->(node.neighborNodes().distinct().count() == 2 ? 1: 0)).sum());

        graph.nodes()
                .map((node)-> (node.neighborNodes().distinct().count() == 2 ? node : null ))
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
                .stream()
                .forEach(node -> {
                    List<Node> neighbors  = node.neighborNodes().distinct().collect(Collectors.toList());
                    Node n1 = neighbors.get(0);
                    Node n2 = neighbors.get(1);
                    //System.err.println(n1+ " - "+ node + " - "+ n2);


                    if(n1.hasEdgeToward(node) && node.hasEdgeToward(n2) && ! n1.hasEdgeToward(n2))
                    {
                        try {
                            graph.addEdge(n1.getId()+"-"+n2.getId(), n1, n2, true);
                        }
                        catch (EdgeRejectedException | IdAlreadyInUseException e)
                        {
                            System.out.println(e.getLocalizedMessage());
                        }

                    }
                    if(n2.hasEdgeToward(node) && node.hasEdgeToward(n1) && ! n2.hasEdgeToward(n1)) {
                        try {
                            graph.addEdge(n2.getId() + "-" + n1.getId(), n2, n1, true);
                        } catch (EdgeRejectedException | IdAlreadyInUseException e) {
                            System.out.println(e.getLocalizedMessage());
                        }
                    }
                    graph.removeNode(node);

                    if(n1.hasEdgeToward(n2) && n2.hasEdgeToward(n1)) {
                        graph.removeEdge(n1.getEdgeToward(n2));
                        if(n2.hasEdgeToward(n1)) {
                            graph.removeEdge(n2.getEdgeToward(n1));
                        }
                        graph.addEdge(n1.getId()+"-"+n2.getIndex(), n1, n2, false);
                    }

                });

        System.out.println("nodes: "+graph.getNodeCount()+" edges: "+graph.getEdgeCount()+ " 2 neighbors: "+graph.nodes().mapToInt((node)->(node.neighborNodes().distinct().count() == 2 ? 1: 0)).sum());

    }
}
