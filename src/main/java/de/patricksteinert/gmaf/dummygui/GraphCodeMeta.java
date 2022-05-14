package de.patricksteinert.gmaf.dummygui;


/**
 * Class to hold the meta information including the calculated values.
 * <p>
 * Created by Patrick Steinert on 04.04.22.
 */

public class GraphCodeMeta {
    /**
     * File name of the Graph Code file. Need to be identical with the file on the calculating system.
     */
    private String fileName;

    /**
     * Calculated metric values in the order similarity, recommendation, inference.
     */
    private float[] metric = new float[]{0f, 0f, 0f};

    /**
     * The GraphCode representation
     */
    private GraphCode graphcode;

    /**
     * Default constructor.
     *
     * @param file filename of the Graph Code in the file system.
     * @param gc   representation of the graph code in the file.
     */
    public GraphCodeMeta(String file, GraphCode gc) {
        fileName = file;
        graphcode = gc;
    }

    /**
     * Getter for the GraphCode filename.
     *
     * @return the filename (not path) as string.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Setter for the GraphCode filename.
     *
     * @param fileName set the filename. Be aware that this changes might result in a wrong reconciliation.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter for the GraphCode metrics.
     *
     * @return
     */
    public float[] getMetric() {
        return metric;
    }

    /**
     * Setter for the calculated metrics in the order similarity, recommendation, inference.
     *
     * @param metric array with the values for similarity, recommendation, inference
     */
    public void setMetric(float[] metric) {
        this.metric = metric;
    }

    /**
     * Getter for the Graph Code representation.
     *
     * @return the GraphCode object.
     */
    public GraphCode getGraphcode() {
        return graphcode;
    }

    /**
     * Setter for the Graph Code representation.
     *
     * @param graphcode the GraphCode object.
     */
    public void setGraphcode(GraphCode graphcode) {
        this.graphcode = graphcode;
    }
}
