package org.graphstream.demo.netstream;

import java.io.IOException;


public class Me {
    public static void main(String[] args) throws IOException {
        final String sourceId = "java_netstream_test";
        long timeId = 0L;
        String serverIP = "localhost";

        String id = "java";
        String label = "Mr or Ms Java";
        String image_url = "http://tarnaeluin.houseofbeor.net/wp-content/uploads/2014/08/Java-Logo.jpg";

        NetStreamSender stream = new NetStreamSender(serverIP, 2001);
        stream.nodeAdded(sourceId, timeId++, id);
        stream.nodeAttributeAdded(sourceId, timeId++, id, "ui.label", label);
        stream.nodeAttributeAdded(sourceId, timeId++, id, "ui.style", "fill-image: url('"+image_url+"');");
        stream.close();
    }
}
