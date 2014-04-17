package de.metalcon.imageGalleryServer.commands;

import de.metalcon.imageGalleryServer.ImageGalleryServer;
import de.metalcon.imageGalleryServer.commands.parameters.CreateImageParameterContainer;

/**
 * command executing a simple image creation
 * 
 * @author sebschlicht
 * 
 */
public class CreateImageCommand extends
        GalleryCommand<CreateImageParameterContainer> {

    /**
     * create simple image creation command
     * 
     * @param galleryServer
     *            gallery server handling the request
     * @param parameters
     *            parameters for image creation
     */
    public CreateImageCommand(
            ImageGalleryServer galleryServer,
            CreateImageParameterContainer parameters) {
        super(galleryServer, parameters);
    }

    @Override
    public void execute() {
        galleryServer.createImage(parameters);
    }

}
