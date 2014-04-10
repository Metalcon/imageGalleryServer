package de.metalcon.imageGalleryServer.commands;

import de.metalcon.imageGalleryServer.ImageGalleryServer;

/**
 * basic command for gallery server to be queued
 * 
 * @author sebschlicht
 * 
 * @param <T>
 *            container class for request parameters
 */
public abstract class GalleryCommand<T > {

    /**
     * gallery server handling the request
     */
    protected ImageGalleryServer galleryServer;

    /**
     * parameters for request
     */
    protected T parameters;

    /**
     * create basic command for gallery server
     * 
     * @param galleryServer
     *            gallery server handling the request
     * @param parameters
     *            parameters for request
     */
    protected GalleryCommand(
            ImageGalleryServer galleryServer,
            T parameters) {
        this.galleryServer = galleryServer;
        this.parameters = parameters;
    }

    /**
     * execute the command via gallery server
     */
    abstract public void execute();

}
