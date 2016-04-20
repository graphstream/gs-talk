package org.graphstream.demo.modularity;

import java.awt.*;
import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;


import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.algorithm.measure.Modularity;
import org.graphstream.graph.Edge;
import org.graphstream.stream.ProxyPipe;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.layout.springbox.implementations.LinLog;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.boids.BoidGenerator;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import javax.swing.*;


public class LinLogLayoutAndBoids {
    public static void main(String args[]) throws IOException, GraphParseException, InterruptedException {
        (new LinLogLayoutAndBoids()).findCommunities("data/config.dgs");
    }

    private Graph graph;
    private Viewer viewer;
    private Viewer bViewer;

    private LinLog layout;
    private double a = 0.2;
    private double r = -2;
    private double force = 1;
    private double cutThreshold = 1;

    private ProxyPipe fromViewer;
    private ConnectedComponents cc;
    private Modularity modularity;
    private BoidGenerator boidGenerator;

    private SpriteManager sm;
    private Sprite ccCount, modValue;


    protected static String styleSheet =
            "node { size: 7px; fill-color: rgb(150,150,150); }" +
                    "edge { fill-color: rgb(255,50,50); size: 2px; }" +
                    "edge.cut { fill-color: rgba(200,200,200,128); }" +
                    "sprite#CC { size: 0px; text-color: rgb(150,100,100); text-size: 20; }" +
                    "sprite#M  { size: 0px; text-color: rgb(100,150,100); text-size: 20; }";


    public void findCommunities(String fileName) throws IOException, GraphParseException, InterruptedException {
        graph = new SingleGraph("communities");
        graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.stylesheet", styleSheet);

        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);

        layout = new LinLog(false);
        layout.setStabilizationLimit(0);
        cc = new ConnectedComponents(graph);
        boidGenerator = new BoidGenerator(fileName);

        sm = new SpriteManager(graph);
        ccCount = sm.addSprite("CC");
        modValue = sm.addSprite("M");


        modularity = new Modularity("module");
        modularity.init(graph);

        layout.configure(a, r, true, force);

        viewer.enableAutoLayout(layout);
        fromViewer = viewer.newThreadProxyOnGraphicGraph();
        fromViewer.addSink(graph);

        boidGenerator.addElementSink(graph);

        cc.setCutAttribute("cut");
        cc.setCountAttribute("module");

        ccCount.setPosition(Units.PX, 20, 20, 0);
        modValue.setPosition(Units.PX, 20, 40, 0);

        boidGenerator.begin();

        bViewer = new Viewer(boidGenerator.getBoidGraph(), Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        initFrame();


        while (!graph.hasAttribute("ui.viewClosed") && boidGenerator.nextEvents()) {
            fromViewer.pump();
            showCommunities();
            ccCount.setAttribute("ui.label",
                    String.format("Modules %d", cc.getConnectedComponentsCount()));
            modValue.setAttribute("ui.label",
                    String.format("Modularity %f", modularity.getMeasure()));
            Thread.sleep(50);

        }
    }

    public void showCommunities() {
        int nEdges = graph.getEdgeCount();
        double length, averageLength = 0;

        for (Edge edge : graph.getEachEdge()) {
            length = GraphPosLengthUtils.edgeLength(edge);
            edge.setAttribute("length", length);
            averageLength += length;
        }
        averageLength /= nEdges;


        for (Edge edge : graph.getEachEdge()) {
            length = edge.getNumber("length");
            if (length > averageLength * cutThreshold) {
                edge.addAttribute("ui.class", "cut");
                edge.addAttribute("cut");
            } else {
                edge.removeAttribute("ui.class");
                edge.removeAttribute("cut");
            }
        }
    }

    private void initFrame() {

        JFrame f = new JFrame("Boids and LinLog");
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1, 3));
        f.add(p);

        View view = viewer.addDefaultView(false);
        ((Component) view).setPreferredSize(new Dimension(600, 600));
        p.add((Component) view);

        View bView = bViewer.addDefaultView(false);

        ((Component) bView).setPreferredSize(new Dimension(600, 600));
        p.add((Component) bView);

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

}