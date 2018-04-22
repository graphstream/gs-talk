/**
 *
 * Copyright (c) 2014 University of Le Havre
 *
 * @file GeoProjectionPipe.java
 * @date Mar 12, 2014
 *
 * @author Yoann Pigné
 *
 */

package org.graphstream.demo.geography;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashMap;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.PipeBase;

import com.jhlabs.map.proj.Projection;
import com.jhlabs.map.proj.ProjectionFactory;


public class GeoProjectionPipe extends PipeBase {




    /**
     *
     */

        HashMap<String, Point2D.Double> nodesLonLat;
        Projection proj;
        double lat_0=30;
        double lon_0=1;
        long timeId=0;
        String SourceID = "GeoProjectionPipe";

        String latitudeAttributeName = "lat";

    public String getLatitudeAttributeName() {
        return latitudeAttributeName;
    }

    public void setLatitudeAttributeName(String latitudeAttributeName) {
        this.latitudeAttributeName = latitudeAttributeName;
    }

    public String getLongitudeAttributeName() {
        return longitudeAttributeName;
    }

    public void setLongitudeAttributeName(String longitudeAttributeName) {
        this.longitudeAttributeName = longitudeAttributeName;
    }

    String longitudeAttributeName = "lon";

        public GeoProjectionPipe(){
            super();

            nodesLonLat = new HashMap<String, Point2D.Double>();
            resetProjection();

        }
        public double getLat0(){
            return lat_0;
        }
        public double getLon0(){
            return lon_0;
        }
        public void setLat0(double val){
            lat_0 = val;

            proj.setProjectionLatitudeDegrees(val);
            //resetProjection();
            sendNodesProjection();
        }
        public void setLon0(double val){
            lon_0 = val;
            proj.setProjectionLongitudeDegrees(val);
            //resetProjection();
            sendNodesProjection();
        }
        public void setLon0Lat0(double lon, double lat){
            lat_0 = lat;
            lon_0 = lon;
            proj.setProjectionLatitudeDegrees(lat_0);
            proj.setProjectionLongitudeDegrees(lon_0);
            sendNodesProjection();
        }
        public void resetProjection(){

            proj = ProjectionFactory.fromPROJ4Specification(("+proj=ortho +lat_0="+(int)lat_0+" +lon_0="+(int)lon_0+" +x_0=10   +y_0=10   +ellps=WGS84").split(" "));
        }
        public void resetProjection(String projConfig) {
            proj = ProjectionFactory.fromPROJ4Specification(projConfig.split(" "));
        }

        private void sendNodesProjection(){
            for(String key : nodesLonLat.keySet()){
                Point2D.Double geoCoords = nodesLonLat.get(key);
                Point2D.Double xyCoords = computeProjection(geoCoords);
                //System.out.println("projection of ("+geoCoords.x+", "+geoCoords.y+") gives: ("+xyCoords.x+", "+xyCoords.y+")");

                Double[] ne = {xyCoords.x, xyCoords.y};

                sendNodeAttributeAdded(sourceId, timeId++, key, "xy", ne);
            }
        }

        private Point2D.Double computeProjection(Point2D.Double geoCoords){
            Point2D.Double xyCoords = new Point2D.Double();

            proj.transform(geoCoords, xyCoords);

            //System.out.println("projection of ("+geoCoords.x+", "+geoCoords.y+") gives: ("+xyCoords.x+", "+xyCoords.y+")");
            return xyCoords;
        }
        private Point2D.Double nodeProjection(String nodeId, Number lon, Number lat, Point2D.Double coords){
            if (coords != null){
            }
            else {
                coords = new Point2D.Double();
            }
            coords.x = (java.lang.Double) (lon!=null?lon.doubleValue():coords.x);
            coords.y = (java.lang.Double) (lat!=null?lat.doubleValue():coords.y);
            nodesLonLat.put(nodeId, coords);
            return computeProjection(coords);
        }


        private void sendProjection(String nodeId, Number lon, Number lat) {

            Point2D.Double previousCoords = nodesLonLat.get(nodeId);
            Point2D.Double newCoords = nodeProjection( nodeId, lon, lat, previousCoords);
            Double[] ne = {newCoords.x, newCoords.y};
            if(previousCoords != null){
                previousCoords = computeProjection(previousCoords);
                Double[] prev = {previousCoords.x, previousCoords.y};
                sendNodeAttributeChanged(sourceId, timeId++, nodeId, "xy", ne, prev);
            }else {
                sendNodeAttributeAdded(sourceId, timeId++, nodeId, "xy", ne);
            }

        }

        private void filterLatLonAttributes(String sourceId, long timeId, String nodeId,
                                            String attribute, Object newValue, Object oldValue){
            if(attribute.equals(longitudeAttributeName)){
                sendProjection(nodeId, (Number) newValue, null);
            } else if (attribute.equals(latitudeAttributeName)) {
                sendProjection(nodeId, null, (Number) newValue);
            } else if(attribute.equals(longitudeAttributeName+latitudeAttributeName)) {
                sendProjection(nodeId,
                        (Double) ((Object[]) newValue)[0],
                        (Double) ((Object[]) newValue)[1]);
            }
        }
        /*
         * (non-Javadoc)
         *
         * @see
         * org.graphstream.stream.PipeAdapter#nodeAttributeAdded(java.lang.String ,
         * long, java.lang.String, java.lang.String, java.lang.Object)
         */
        @Override
        public void nodeAttributeAdded(String sourceId, long timeId, String nodeId,
                                       String attribute, Object value) {

            filterLatLonAttributes(sourceId, timeId, nodeId, attribute,
                    value, null);


        }
        /*
         * (non-Javadoc)
         *
         * @see org.graphstream.stream.PipeAdapter#nodeAttributeChanged(java.lang
         * .String, long, java.lang.String, java.lang.String, java.lang.Object,
         * java.lang.Object)
         */
        @Override
        public void nodeAttributeChanged(String sourceId, long timeId,
                                         String nodeId, String attribute, Object oldValue, Object newValue) {
            filterLatLonAttributes(sourceId, timeId, nodeId, attribute,
                    newValue, oldValue);
        }


        public void edgeAttributeAdded(String graphId, long timeId, String edgeId,
                                       String attribute, Object value) {
        }

        public void edgeAttributeChanged(String graphId, long timeId,
                                         String edgeId, String attribute, Object oldValue, Object newValue) {
        }

        public void edgeAttributeRemoved(String graphId, long timeId,
                                         String edgeId, String attribute) {
        }

        public void graphAttributeAdded(String graphId, long timeId,
                                        String attribute, Object value) {
        }

        public void graphAttributeChanged(String graphId, long timeId,
                                          String attribute, Object oldValue, Object newValue) {
        }

        public void graphAttributeRemoved(String graphId, long timeId,
                                          String attribute) {
        }
        public void nodeAttributeRemoved(String graphId, long timeId,
                                         String nodeId, String attribute) {
        }

        public void edgeAdded(String graphId, long timeId, String edgeId,
                              String fromNodeId, String toNodeId, boolean directed) {
        }

        public void edgeRemoved(String graphId, long timeId, String edgeId) {
        }

        public void graphCleared(String graphId, long timeId) {
            sendGraphCleared(graphId, timeId);
        }

        public void nodeAdded(String graphId, long timeId, String nodeId) {
            sendNodeAdded(graphId, timeId, nodeId);
        }

        public void nodeRemoved(String graphId, long timeId, String nodeId) {
            sendNodeRemoved(graphId, timeId, nodeId);
        }

        public void stepBegins(String graphId, long timeId, double step) {
            sendStepBegins(graphId, timeId, step);
        }

}
