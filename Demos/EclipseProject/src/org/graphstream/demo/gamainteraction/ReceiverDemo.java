package org.graphstream.demo.gamainteraction;

import java.io.IOException;
import java.net.UnknownHostException;

import org.graphstream.stream.netstream.NetStreamReceiver;

public class ReceiverDemo {

	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException {
		NetStreamReceiver receiver1 = new NetStreamReceiver(2001);
		new NetStreamViewer(receiver1, false);
	}
}