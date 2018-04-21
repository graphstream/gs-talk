package org.graphstream.demo.netstream;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.binary.ByteProxy;
import org.graphstream.stream.netstream.NetStreamUtils;
import org.graphstream.util.VerboseSink;

import java.io.IOException;
import java.net.InetAddress;


public class Me {
    public static void main(String[] args) throws IOException, InterruptedException {
        String id = "java";
        String label = "Mr or Ms Java";
        String image_url = "http://tarnaeluin.houseofbeor.net/wp-content/uploads/2014/08/Java-Logo.jpg";

        ByteProxy client = new ByteProxy(NetStreamUtils.getDefaultNetStreamFactory(), ByteProxy.Mode.CLIENT,
                    InetAddress.getLocalHost(), 2001);
        client.start();

        Graph graphClient = new DefaultGraph("client");

        graphClient.addSink(client);

        Node n = graphClient.addNode(id);
        n.setAttribute(id, "ui.label", label);
        n.setAttribute(id, "ui.style", "fill-image: url('"+image_url+"');");

        client.stop();

    }
}
