package de.patricksteinert.gmaf.dummygui;

import de.swa.gc.GraphCode;

/**
 * Created by Patrick Steinert on 04.04.22.
 */

public class GraphCodeMeta {
    private String fileName;
    private float[] metric = new float[] {0f,0f,0f};
    private GraphCode graphcode;

    public GraphCodeMeta(String file, GraphCode gc) {
        fileName = file;
        graphcode = gc;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public float[] getMetric() {
        return metric;
    }
    public void setMetric(float[] metric) {
        this.metric = metric;
    }
    public GraphCode getGraphcode() {
        return graphcode;
    }
    public void setGraphcode(GraphCode graphcode) {
        this.graphcode = graphcode;
    }
}
