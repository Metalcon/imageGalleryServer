package de.metalcon.imageGalleryServer.zmq;

import net.hh.request_dispatcher.server.RequestHandler;
import de.metalcon.api.responses.Response;
import de.metalcon.imageGalleryServer.ImageGalleryServer;
import de.metalcon.imageGalleryServer.api.requests.GalleryServerRequest;
import de.metalcon.imageGalleryServer.api.requests.RequestAllImages;
import de.metalcon.imageGalleryServer.api.responses.GalleryResponse;

public class ImageGalleryRequestHandler implements
        RequestHandler<GalleryServerRequest, Response> {

    private static final long serialVersionUID = 6290614306046531451L;

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
            return new GalleryResponse(galleryServer.readImagesOfEntity(
                    readRequest.getEntityId(), 0, 0));
        }

        throw new IllegalArgumentException("unknown request type \""
                + request.getClass().getName() + "\"");
    }
}