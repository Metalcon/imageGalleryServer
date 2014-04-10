package de.metalcon.imageGalleryServer.exception;

/**
 * exception factory to create exceptions with useful error messages
 * 
 * @author sebschlicht
 * 
 */
public class ExceptionFactory {

    /**
     * create usage exception when image identifier is in use
     * 
     * @param imageId
     *            image identifier that is in use
     * @return IllegalArgumentException with useful error message
     */
    public static RuntimeException usageImageIdentifierInUse(long imageId) {
        throw new IllegalArgumentException("image identifier \"" + imageId
                + "\" already in use");
    }

}
