package de.metalcon.imageGalleryServer.schema;

/**
 * author types a gallery is separated into
 * 
 * @author sebschlicht
 * 
 */
public enum AuthorType {

    /**
     * images uploaded by the gallery owner
     */
    OWN("ownImages"),

    /**
     * images uploaded by an other entity
     */
    FOREIGN("foreignImages");

    /**
     * edge label
     */
    protected String label;

    /**
     * register author type
     * 
     * @param label
     *            edge label
     */
    private AuthorType(
            String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
