package de.metalcon.imageGalleryServer.commands.parameters;

import java.io.InputStream;

import de.metalcon.imageGalleryServer.ImageInfo;

/**
 * parameters for simple image creation
 * 
 * @author sebschlicht
 * 
 */
public class CreateImageParameterContainer {

    /**
     * identifier of the uploading entity
     */
    protected long entityId;

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
     * @param entityId
     *            identifier of the uploading entity
     * @param imageInfo
     *            image information
     * @param imageStream
     *            raw image data
     */
    public CreateImageParameterContainer(
            long entityId,
            ImageInfo imageInfo,
            InputStream imageStream) {
        this.entityId = entityId;
        this.imageInfo = imageInfo;
        this.imageStream = imageStream;
    }

    /**
     * @return identifier of the uploading entity
     */
    public long getEntityId() {
        return entityId;
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
