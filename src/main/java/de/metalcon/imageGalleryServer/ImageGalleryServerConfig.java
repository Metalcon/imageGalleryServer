package de.metalcon.imageGalleryServer;

import de.metalcon.imageStorageServer.ImageStorageServerConfig;
import de.metalcon.zmqworker.ZmqConfig;

/**
 * image gallery server configuration object
 * 
 * @author sebschlicht
 * 
 */
public class ImageGalleryServerConfig extends ImageStorageServerConfig
        implements ZmqConfig {

    private static final long serialVersionUID = -7405977360066179480L;

    /**
     * path to gallery database
     */
    public String galleryDatabase_path;

    /**
     * ZMQ endpoint the worker listens on
     */
    public String endpoint;

    /**
     * load image gallery server configuration
     * 
     * @param configPath
     *            path to the configuration file
     */
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

    @Override
    public String getEndpoint() {
        return endpoint;
    }

}
