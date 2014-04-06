package de.metalcon.imageGalleryServer;

public class Image {

    /**
     * identifier
     */
    protected long identifier;

    /**
     * all meta data
     */
    protected String metaData;

    /**
     * raw image data
     */
    protected byte[] rawImage;

    /**
     * create image object
     * 
     * @param metaData
     *            all meta data
     * @param rawImage
     *            raw image data
     * @param verify
     *            set to false to skip meta data format verification
     */
    public Image(
            String metaData,
            byte[] rawImage,
            boolean verify) {
        this.metaData = metaData;
        this.rawImage = rawImage;

        if (verify) {
            // TODO verify valid JSON
        }
    }

    /**
     * create image object
     * 
     * @param metaData
     *            all meta data (gets verified against meta data format)
     * @param rawImage
     *            raw image data
     */
    public Image(
            String metaData,
            byte[] rawImage) {
        this(metaData, rawImage, true);
    }

}
