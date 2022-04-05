package de.patricksteinert.gmaf.dummygui;

import java.util.Vector;

/**
 * Created by Patrick Steinert on 04.04.22.
 */
public abstract class CollectionProcessor {
    public abstract void setQueryObject(GraphCodeMeta graphCodeMeta);

    public abstract Vector<GraphCodeMeta> getResultList();

    public abstract void preloadIndex(Vector<GraphCodeMeta> collection);


}
