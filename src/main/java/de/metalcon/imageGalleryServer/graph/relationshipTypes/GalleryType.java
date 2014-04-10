package de.metalcon.imageGalleryServer.graph.relationshipTypes;

import org.neo4j.graphdb.RelationshipType;

/**
 * categories an entity child can be sorted in
 * 
 * @author sebschlicht
 * 
 */
public enum GalleryType implements RelationshipType {

    /**
     * image was created in news feed
     */
    NEWS_FEED,

    /**
     * image was created in wiki editor
     */
    WIKI,

    /**
     * gallery was manually created by entity
     */
    ENTITY_GALLERY;

}
