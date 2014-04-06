package de.metalcon.imageGalleryServer.exceptions;

/**
 * factory to easily generate exceptions with useful error messages
 * 
 * @author sebschlicht
 * 
 */
public class ExceptionFactory {

    public static IllegalArgumentException
        usageImageIdentifierUsed(long imageId) {
        return new IllegalArgumentException("image identifier \"" + imageId
                + "\" already in use");
    }

    public static IllegalArgumentException usageImageIdentifierUnknown(
            long imageId) {
        return new IllegalArgumentException("unknown image identifier \""
                + imageId + "\"");
    }

}
