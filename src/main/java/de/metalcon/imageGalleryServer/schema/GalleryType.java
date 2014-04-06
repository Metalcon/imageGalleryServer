package de.metalcon.imageGalleryServer.schema;

/**
 * gallery types an entity can have
 * 
 * @author sebschlicht
 * 
 */
public enum GalleryType {

    /**
     * generated gallery containing all images linked to entity
     */
    ALL("genGalleryAll"),

    /**
     * generated gallery containing images directly posted to news feed by/of an
     * entity
     */
    NEWS_FEED("genGalleryNewsFeed"),

    /**
     * generated gallery containing images uploaded to the entity's wiki page
     */
    WIKI("genGalleryWiki"),

    /**
     * generated gallery containing images the entity was tagged on
     */
    TAGGED("genGalleryTagged"),

    /**
     * gallery created by the entity manually
     */
    MANUAL("manualGallery");

    /**
     * edge label
     */
    protected String label;

    /**
     * register gallery type
     * 
     * @param label
     *            edge label
     */
    private GalleryType(
            String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
