package de.metalcon.imageGalleryServer.schema;

/**
 * node types that have to be separated
 * 
 * @author sebschlicht
 * 
 */
public enum NodeType {

    /**
     * entity
     */
    ENTITY("entity"),

    /**
     * manual gallery owned by an entity
     */
    GALLERY("gallery"),

    /**
     * image uploaded by an entity, that may be linked to multiple
     * (generated/manual) galleries
     */
    IMAGE("image");

    /**
     * type identifier
     */
    protected String identifier;

    /**
     * register node type
     * 
     * @param identifier
     *            type identifier
     */
    private NodeType(
            String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return type identifier
     */
    public String getIdentifier() {
        return identifier;
    }

}
