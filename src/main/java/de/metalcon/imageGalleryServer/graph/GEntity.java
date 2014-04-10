package de.metalcon.imageGalleryServer.graph;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import de.metalcon.imageGalleryServer.graph.relationshipTypes.AuthorType;

public class GEntity extends GNode {

    protected static final String AUTHOR_PROP_TIMESTAMP = "timestamp";

    public GEntity(
            GraphDatabaseService graph,
            long identifier) {
        super(graph, GNodeType.ENTITY, identifier);
    }

    private GEntity(
            Node entityNode) {
        super(entityNode);
    }

    public void addImage(GImage image, boolean checkIfExisting) {
        if (checkIfExisting) {
            // TODO
        }

        Relationship edge =
                node.createRelationshipTo(image.getNode(), AuthorType.UPLOAD);
        edge.setProperty(AUTHOR_PROP_TIMESTAMP, image.getTimestamp());
    }

    public static GEntity loadFromNode(Node entityNode) {
        return new GEntity(entityNode);
    }

}
