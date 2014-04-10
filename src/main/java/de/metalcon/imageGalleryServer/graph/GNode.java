package de.metalcon.imageGalleryServer.graph;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

public class GNode {

    /**
     * node type property identifier
     */
    protected static final String TYPE = "type";

    /**
     * node wrapped by this object
     */
    protected Node node;

    /**
     * create basic gallery node
     * 
     * @param graph
     *            graph holding the gallery
     */
    public GNode(
            GraphDatabaseService graph) {
        node = graph.createNode();
    }

}
