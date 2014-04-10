package de.metalcon.imageGalleryServer;

/**
 * image information object to create images and use images read
 * 
 * @author sebschlicht
 * 
 */
public class ImageInfo {

    /**
     * timestamp of image upload
     */
    protected long timestamp;

    /**
     * image identifier
     */
    protected long identifier;

    /**
     * URL to plain image
     */
    protected String urlSource;

    /**
     * URL image refers to<br>
     * (optional)
     */
    protected String urlLink;

    /**
     * image title<br>
     * (optional)
     */
    protected String title;

    /**
     * additional meta data<br>
     * (optional)
     */
    protected String metaData;

    /**
     * @return timestamp of image upload
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return image identifier
     */
    public long getIdentifier() {
        return identifier;
    }

    /**
     * @return additional meta data<br>
     *         (optional, may be <b>null</b>)
     */
    public String getMetaData() {
        return metaData;
    }

}
