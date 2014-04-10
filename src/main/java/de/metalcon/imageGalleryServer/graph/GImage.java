package de.metalcon.imageGalleryServer.graph;

import org.neo4j.graphdb.GraphDatabaseService;

import de.metalcon.imageGalleryServer.ImageInfo;

public class GImage extends GNode {

    protected static final String PROP_TIMESTAMP = "timestamp";

    public GImage(
            GraphDatabaseService graph,
            long identifier,
            ImageInfo imageInfo) {
        super(graph, GNodeType.IMAGE, identifier);
        node.setProperty(PROP_TIMESTAMP, imageInfo.getTimestamp());
    }

    public long getTimestamp() {
        return (long) node.getProperty(PROP_TIMESTAMP);
    }

}
