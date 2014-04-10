package de.metalcon.imageGalleryServer.graph.relationshipTypes;

import org.neo4j.graphdb.RelationshipType;

public enum AuthorType implements RelationshipType {

    /**
     * image was uploaded by entity
     */
    UPLOAD,

    /**
     * image is linked to entity
     */
    LINK;

}
