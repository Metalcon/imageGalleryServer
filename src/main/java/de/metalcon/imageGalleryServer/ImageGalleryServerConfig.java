package de.metalcon.imageGalleryServer;

import de.metalcon.imageStorageServer.ImageStorageServerConfig;

/**
 * image gallery server configuration object
 * 
 * @author sebschlicht
 * 
 */
public class ImageGalleryServerConfig extends ImageStorageServerConfig {

    private static final long serialVersionUID = -7405977360066179480L;

    /**
     * path to gallery database
     */
    public String galleryDatabase_path;

    public ImageGalleryServerConfig(
            String configPath) {
        super(configPath);
    }

    /**
     * @return path to gallery database
     */
    public String getGalleryDatabasePath() {
        return galleryDatabase_path;
    }

}
