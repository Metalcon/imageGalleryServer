package de.metalcon.imageGalleryServer;

/**
 * image information object to create images and use images read before
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

    public ImageInfo(
            long timestamp,
            long identifier,
            String urlSource,
            String urlLink,
            String title) {
        this.timestamp = timestamp;
        this.identifier = identifier;
        this.urlSource = urlSource;
        this.urlLink = urlLink;
        this.title = title;
    }

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
     * @return URL to plain image
     */
    public String getUrlSource() {
        return urlSource;
    }

    /**
     * @return URL image refers to<br>
     *         (optional, may be <b>null</b>)
     */
    public String getUrlLink() {
        return urlLink;
    }

    /**
     * @return image title<br>
     *         (optional, may be <b>null</b>)
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return additional meta data<br>
     *         (optional, may be <b>null</b>)
     */
    public String getMetaData() {
        return metaData;
    }

}
