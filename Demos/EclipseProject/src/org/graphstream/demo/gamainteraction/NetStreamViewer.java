package org.graphstream.demo.gamainteraction;

import org.graphstream.stream.netstream.NetStreamReceiver;
import org.graphstream.ui.swingViewer.Viewer;

public class NetStreamViewer extends Viewer {
	public NetStreamViewer(NetStreamReceiver receiver) {
		this(receiver, true);
	}

	public NetStreamViewer(NetStreamReceiver receiver, boolean autoLayout) {
		super(receiver.getDefaultStream());
		addDefaultView(true);
		if (autoLayout)
			enableAutoLayout();
	}

	public NetStreamViewer(NetStreamReceiver receiver, boolean autoLayout, int width, int height) {
		this(receiver, autoLayout);
		getDefaultView().resizeFrame(width, height);
	}
}