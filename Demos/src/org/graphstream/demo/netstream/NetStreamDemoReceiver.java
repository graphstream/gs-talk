package org.graphstream.demo.netstream;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.Replayable;
import org.graphstream.stream.binary.ByteProxy;
import org.graphstream.stream.netstream.NetStreamUtils;
import org.graphstream.util.VerboseSink;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by pigne on 1/7/16.
 */
public class NetStreamDemoReceiver {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        System.setProperty("org.graphstream.ui", "swing");

        // ----- On the receiver side -----
        //
        // - a graph that will display the received events
        Graph g = new MultiGraph("G",false,true);
        g.setAttribute("ui.antialias", true);
        g.setAttribute("layout.stabilization-limit", 0);
        g.setAttribute("stylesheet", stylesheet);
        g.display();

        VerboseSink logout = new VerboseSink();
        g.addSink(logout);

        ByteProxy server = new ByteProxy(NetStreamUtils.getDefaultNetStreamFactory(), 2001);

        g.addSink(server);
        server.addSink(g);


        server.start();



    }
    public static String stylesheet = "" +
            "graph { " +
            "   padding: 50px; " +
            "   fill-mode: image-scaled-ratio-min;" +
            "   fill-image: url('data/logo_utp2.png');" +
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
