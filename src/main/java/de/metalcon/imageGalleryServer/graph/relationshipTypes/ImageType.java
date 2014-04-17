package de.metalcon.imageGalleryServer.graph.relationshipTypes;

import org.neo4j.graphdb.RelationshipType;

/**
 * types an image relationship can be of
 * 
 * @author sebschlicht
 * 
 */
public enum ImageType implements RelationshipType {

    /**
     * image was uploaded by entity
     */
    UPLOAD,

    /**
     * image is linked to entity
     */
    LINK;

}
