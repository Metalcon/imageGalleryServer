package de.metalcon.imageGalleryServer;

public class ImageInfo {

    protected long timestamp;

    protected long identifier;

    protected String urlSource;

    /**
     * optional
     */
    protected String urlLink;

    /**
     * optional
     */
    protected String title;

    /**
     * optional
     */
    protected String metaData;

    public long getTimestamp() {
        return timestamp;
    }

    public long getIdentifier() {
        return identifier;
    }

    public String getMetaData() {
        return metaData;
    }

}
