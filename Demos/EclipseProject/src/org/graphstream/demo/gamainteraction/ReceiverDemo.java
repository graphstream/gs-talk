package org.graphstream.demo.gamainteraction;

import java.io.IOException;
import java.net.UnknownHostException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.netstream.NetStreamReceiver;
import org.graphstream.ui.swingViewer.Viewer;

public class ReceiverDemo {

	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException {
		NetStreamReceiver receiver = new NetStreamReceiver("localhost", 2001);
		Viewer v = new Viewer(receiver.getDefaultStream());
		v.addDefaultView(true);
	}
}