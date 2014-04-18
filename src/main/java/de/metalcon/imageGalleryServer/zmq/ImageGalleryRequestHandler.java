package de.metalcon.imageGalleryServer.zmq;

import net.hh.request_dispatcher.RequestHandler;

import org.apache.log4j.Logger;

import de.metalcon.api.responses.Response;
import de.metalcon.api.responses.SuccessResponse;
import de.metalcon.imageGalleryServer.ImageGalleryServer;
import de.metalcon.imageGalleryServer.api.requests.CreateImageRequest;
import de.metalcon.imageGalleryServer.api.requests.GalleryServerRequest;
import de.metalcon.imageGalleryServer.api.requests.RequestAllImages;
import de.metalcon.imageGalleryServer.api.responses.GalleryResponse;

public class ImageGalleryRequestHandler implements
        RequestHandler<GalleryServerRequest, Response> {

    private static final long serialVersionUID = 6290614306046531451L;

    protected static Logger LOG = Logger
            .getLogger(ImageGalleryRequestHandler.class);

    protected ImageGalleryServer galleryServer;

    public ImageGalleryRequestHandler(
            ImageGalleryServer galleryServer) {
        this.galleryServer = galleryServer;
    }

    @Override
    public Response handleRequest(GalleryServerRequest request)
            throws Exception {
        if (request instanceof RequestAllImages) {
            RequestAllImages readRequest = (RequestAllImages) request;
            LOG.debug("request: all images of entity #"
                    + readRequest.getEntityId());
            return new GalleryResponse(galleryServer.readImagesOfEntity(
                    readRequest.getEntityId(), 0, 0));
        } else if (request instanceof CreateImageRequest) {
            CreateImageRequest createRequest = (CreateImageRequest) request;
            LOG.debug("request: create image for entity #"
                    + createRequest.getEntityId());
            if (createRequest.getGalleryType() != null) {

                boolean successFlag = false;
                switch (createRequest.getGalleryType()) {

                    case ALL:
                        successFlag =
                                galleryServer.createImage(
                                        createRequest.getEntityId(),
                                        createRequest.getImageInfo(),
                                        createRequest.getImageStream());
                        break;

                    default:
                        throw new IllegalArgumentException(
                                "only GalleryType.ALL is supported in this version");

                }

                if (successFlag) {
                    return new SuccessResponse();
                }
                throw new IllegalArgumentException(
                        "failed to create image. maybe stream was no image?");
            } else {
                // TODO create image in entity gallery
            }
        }

        throw new IllegalArgumentException("unknown request type \""
                + request.getClass().getName() + "\"");
    }
}
