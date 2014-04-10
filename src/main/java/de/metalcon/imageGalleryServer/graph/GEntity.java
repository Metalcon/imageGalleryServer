package de.metalcon.imageGalleryServer.graph;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import de.metalcon.imageGalleryServer.graph.relationshipTypes.ImageType;

/**
 * gallery entity owning galleries and images
 * 
 * @author sebschlicht
 * 
 */
public class GEntity extends GNode {

    protected static final String AUTHOR_PROP_TIMESTAMP = "timestamp";

    /**
     * create gallery entity
     * 
     * @param graph
     *            graph holding the gallery
     * @param identifier
     *            identifier for the new entity
     */
    public GEntity(
            GraphDatabaseService graph,
            long identifier) {
        super(graph, GNodeType.ENTITY, identifier);
    }

    /**
     * load entity from node
     * 
     * @param entityNode
     *            node representing an entity
     */
    private GEntity(
            Node entityNode) {
        super(entityNode);
    }

    /**
     * add an image for the entity
     * 
     * @param image
     *            image to be added
     * @param checkIfExisting
     *            check if the image is already linked if set to <b>true</b>
     */
    public void addImage(GImage image, boolean checkIfExisting) {
        if (checkIfExisting) {
            // TODO
        }

        Relationship edge =
                node.createRelationshipTo(image.getNode(), ImageType.UPLOAD);
        edge.setProperty(AUTHOR_PROP_TIMESTAMP, image.getTimestamp());
    }

    /**
     * load entity from an existing entity node
     * 
     * @param entityNode
     *            node representing an entity
     * @return entity loaded from node specified
     */
    public static GEntity loadFromNode(Node entityNode) {
        return new GEntity(entityNode);
    }

}
