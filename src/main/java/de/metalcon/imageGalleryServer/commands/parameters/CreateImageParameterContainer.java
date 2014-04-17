package de.metalcon.imageGalleryServer.commands.parameters;

import java.io.InputStream;

import de.metalcon.imageGalleryServer.api.ImageInfo;

/**
 * parameters for simple image creation
 * 
 * @author sebschlicht
 * 
 */
public class CreateImageParameterContainer {

    /**
     * image information
     */
    protected ImageInfo imageInfo;

    /**
     * raw image data
     */
    protected InputStream imageStream;

    /**
     * create parameter container for simple image creation
     * 
     * @param imageInfo
     *            image information
     * @param imageStream
     *            raw image data
     */
    public CreateImageParameterContainer(
            ImageInfo imageInfo,
            InputStream imageStream) {
        this.imageInfo = imageInfo;
        this.imageStream = imageStream;
    }

    /**
     * @return identifier of the uploading entity
     */
    public long getEntityId() {
        return imageInfo.getIdentifier();
    }

    /**
     * @return image information
     */
    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    /**
     * @return raw image data
     */
    public InputStream getImageStream() {
        return imageStream;
    }

}
