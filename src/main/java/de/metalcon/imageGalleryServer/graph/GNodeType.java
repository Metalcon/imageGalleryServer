package de.metalcon.imageGalleryServer.graph;

import org.neo4j.graphdb.Label;

/**
 * types a node in the gallery can be of
 * 
 * @author sebschlicht
 * 
 */
public enum GNodeType implements Label {

    /**
     * node represents an entity
     */
    ENTITY("entity"),

    /**
     * node represents an image
     */
    IMAGE("image"),

    /**
     * node represents a gallery manually created by an entity
     */
    GALLERY("gallery");

    /**
     * unique identifier
     */
    private String identifier;

    /**
     * register node type
     * 
     * @param identifier
     *            unique identifier
     */
    private GNodeType(
            String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return unique identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return getIdentifier();
    }

}
