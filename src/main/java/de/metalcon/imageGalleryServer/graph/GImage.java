package de.metalcon.imageGalleryServer.graph;

import org.neo4j.graphdb.GraphDatabaseService;

import de.metalcon.imageGalleryServer.ImageInfo;

/**
 * gallery image owned by an entity<br>
 * may be part of a gallery
 * 
 * @author sebschlicht
 * 
 */
public class GImage extends GNode {

    protected static final String PROP_TIMESTAMP = "timestamp";

    /**
     * create gallery entity
     * 
     * @param graph
     *            graph holding the gallery
     * @param identifier
     *            identifier for the new entity
     * @param imageInfo
     *            image information
     */
    public GImage(
            GraphDatabaseService graph,
            long identifier,
            ImageInfo imageInfo) {
        super(graph, GNodeType.IMAGE, identifier);
        node.setProperty(PROP_TIMESTAMP, imageInfo.getTimestamp());
    }

    /**
     * @return timestamp of image upload
     */
    public long getTimestamp() {
        return (long) node.getProperty(PROP_TIMESTAMP);
    }

}
