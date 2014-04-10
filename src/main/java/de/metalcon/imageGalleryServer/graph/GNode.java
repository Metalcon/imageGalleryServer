package de.metalcon.imageGalleryServer.graph;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

/**
 * basic node in gallery
 * 
 * @author sebschlicht
 * 
 */
public class GNode {

    protected static final String PROP_TYPE = "type";

    public static final String PROP_IDENTIFIER = "identifier";

    /**
     * node wrapped by this object
     */
    protected Node node;

    /**
     * create basic gallery node
     * 
     * @param graph
     *            graph holding the gallery
     * @param type
     *            node type
     * @param identifier
     *            node identifier for indexing
     */
    public GNode(
            GraphDatabaseService graph,
            GNodeType type,
            long identifier) {
        node = graph.createNode(type);
        node.setProperty(PROP_IDENTIFIER, identifier);
    }

    /**
     * load basic gallery node
     * 
     * @param node
     *            node representing this object
     */
    protected GNode(
            Node node) {
        this.node = node;
    }

    /**
     * @return node wrapped by this object
     */
    public Node getNode() {
        return node;
    }

    /**
     * @return node identifier
     */
    public long getIdentifier() {
        return (long) node.getProperty(PROP_IDENTIFIER);
    }

}
