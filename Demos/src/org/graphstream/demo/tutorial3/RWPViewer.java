package org.graphstream.demo.tutorial3;

import java.io.IOException;
import java.net.UnknownHostException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.netstream.NetStreamReceiver;
import org.graphstream.stream.thread.ThreadProxyPipe;

public class RWPViewer {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		Graph g = new SingleGraph("RWP");
		NetStreamReceiver rcv = new NetStreamReceiver("localhost", 2000);
		ThreadProxyPipe pipe = rcv.getDefaultStream();
		pipe.addSink(g);
		g.display(false);
		
		while (true) {
			pipe.blockingPump();
		}
	}
}
