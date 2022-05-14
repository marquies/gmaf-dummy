package de.patricksteinert.gmaf.dummygui;

import java.util.Vector;

/**
 * Modified abstract class from GMAF. For reconciliaten the GraphCode object
 * is replaced by a GraphCodeMeta object.
 * <p>
 * Created by Patrick Steinert on 04.04.22.
 */
public abstract class CollectionProcessor {

    /**
     * Set the GraphCodeMeta query object
     *
     * @param graphCodeMeta the object to calculate the metrics against
     */
    public abstract void setQueryObject(GraphCodeMeta graphCodeMeta);

    /**
     * Returns the list with the calculated result
     *
     * @return Vector with the calculated results.
     */
    public abstract Vector<GraphCodeMeta> getResultList();

    /**
     * Loads the data for the execution.
     *
     * @param collection collection of GraphCodeMeta objects for the metrics calculation.
     */
    public abstract void preloadIndex(Vector<GraphCodeMeta> collection);


}
