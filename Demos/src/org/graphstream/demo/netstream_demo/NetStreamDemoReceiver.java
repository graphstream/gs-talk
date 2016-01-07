package org.graphstream.demo.netstream_demo;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.netstream.NetStreamReceiver;
import org.graphstream.stream.netstream.packing.Base64Unpacker;
import org.graphstream.stream.thread.ThreadProxyPipe;
import org.graphstream.util.VerboseSink;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by pigne on 1/7/16.
 */
public class NetStreamDemoReceiver {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        // ----- On the receiver side -----
        //
        // - a graph that will display the received events
        Graph g = new MultiGraph("G",false,true);
        g.addAttribute("ui.antialias", true);
        g.addAttribute("layout.stabilization-limit", 0);
        g.addAttribute("stylesheet", stylesheet);
        g.display();

        VerboseSink logout = new VerboseSink();
        g.addSink(logout);

        // - the receiver that waits for events
        NetStreamReceiver net = new NetStreamReceiver("0.0.0.0",2001,false);



        // - received events end up in the "default" pipe
        ThreadProxyPipe pipe = net.getStream("default");
        // - plug the pipe to the sink of the graph
        pipe.addSink(g);
        // -The receiver pro-actively checks for events on the ThreadProxyPipe
        while (true) {
            pipe.blockingPump();
            System.out.println("NetStreamDemoReceiver.main looping");

        }
    }
    public static String stylesheet = "" +
            "graph { " +
            "   padding: 50px; " +
            "   fill-mode: image-scaled-ratio-min;" +
            "   fill-image: url('data/logo_NetSci_X_2016.png');" +
            "} " +
            "node { " +
            "   size: 64px; " +
            "   fill-mode: image-scaled;" +
            "   fill-image: url('data/fun.gif'); " +
            "   text-size:      14;" +
            "   text-style:     bold-italic;" +
            "   text-color:     #555;" +
            "   text-alignment: under;" +
            "}" +
            "";
}
