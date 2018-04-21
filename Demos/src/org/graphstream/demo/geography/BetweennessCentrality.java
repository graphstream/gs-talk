package org.graphstream.demo.geography;

import org.graphstream.algorithm.measure.AbstractCentrality;
import org.graphstream.graph.Graph;

public class BetweennessCentrality extends AbstractCentrality {

    private org.graphstream.algorithm.BetweennessCentrality betweenness;
    String attribute;
    String weightAttribute;
    AbstractCentrality.NormalizationMode normalize;
    protected BetweennessCentrality(String weightAttribute, String attribute, AbstractCentrality.NormalizationMode normalize) {
        super(attribute, normalize);

        this.weightAttribute = weightAttribute;
        this.attribute = attribute;
        this.normalize = normalize;

        betweenness = new org.graphstream.algorithm.BetweennessCentrality(attribute);
        if(weightAttribute != null && !weightAttribute.equals("")) {
            betweenness.setWeightAttributeName(weightAttribute);
        }
        this.attribute = attribute;
        this.normalize = normalize;
    }

    @Override
    public void init(Graph graph) {
        super.init(graph);
        betweenness.init(graph);
        betweenness.registerProgressIndicator(v -> System.out.printf("progress: %02.1f%%%n",v*100.0f));
    }

    @Override
    protected void computeCentrality() {
        betweenness.compute();
        for (int idx = 0; idx < graph.getNodeCount(); idx++) {
            data[idx] = (double) graph.getNode(idx).getAttribute(attribute);
        }
        if(normalize != null){
            copyValuesTo(attribute, normalize);
        }

    }
}
