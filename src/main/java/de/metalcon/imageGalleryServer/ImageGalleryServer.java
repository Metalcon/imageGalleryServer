package de.metalcon.imageGalleryServer;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import net.hh.request_dispatcher.RequestHandler;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.Schema;

import de.metalcon.api.responses.Response;
import de.metalcon.imageGalleryServer.api.GalleryInfo;
import de.metalcon.imageGalleryServer.api.ImageInfo;
import de.metalcon.imageGalleryServer.api.requests.GalleryServerRequest;
import de.metalcon.imageGalleryServer.commands.GalleryCommand;
import de.metalcon.imageGalleryServer.commands.parameters.CreateImageParameterContainer;
import de.metalcon.imageGalleryServer.exceptions.ExceptionFactory;
import de.metalcon.imageGalleryServer.graph.GEntity;
import de.metalcon.imageGalleryServer.graph.GImage;
import de.metalcon.imageGalleryServer.graph.GNodeType;
import de.metalcon.imageGalleryServer.graph.traversal.ImageTraversalAll;
import de.metalcon.imageGalleryServer.zmq.ImageGalleryRequestHandler;
import de.metalcon.imageStorageServer.ImageStorageServer;
import de.metalcon.imageStorageServer.protocol.create.CreateResponse;
import de.metalcon.zmqworker.Server;

public class ImageGalleryServer extends Server<GalleryServerRequest> implements
        ImageGallery {

    /**
     * default value for configuration file path
     */
    protected static final String DEFAULT_CONFIG_PATH =
            "/usr/share/metalcon/imageGalleryServer/config.txt";

    protected ImageStorageServer storageServer;

    protected GraphDatabaseService graph;

    protected BlockingQueue<GalleryCommand<?>> pendingCommands;

    public ImageGalleryServer(
            ImageGalleryServerConfig config) {
        super(config);

        // load storage server
        storageServer = new ImageStorageServer(config);

        // load database
        graph =
                new GraphDatabaseFactory().newEmbeddedDatabase(config
                        .getGalleryDatabasePath());

        pendingCommands = new LinkedBlockingQueue<GalleryCommand<?>>();

        // create and load indices
        try (Transaction tx = graph.beginTx()) {
            Schema schema = graph.schema();
            schema.indexFor(GNodeType.ENTITY).on(GEntity.PROP_IDENTIFIER);

            schema.awaitIndexesOnline(10, TimeUnit.SECONDS);
            LOG.debug("graph indices online");

            ImageTraversalAll.init(graph);

            tx.success();
            tx.close();
        }
        LOG.debug("database online");

        // initialize request handler
        RequestHandler<GalleryServerRequest, Response> requestHandler =
                new ImageGalleryRequestHandler(this);

        // start ZMQ communication
        start(requestHandler);
    }

    /////////////// HELPER METHODS /////////////////////

    protected void storeImage(ImageInfo image, InputStream imageStream) {
        CreateResponse response = new CreateResponse();
        if (!storageServer.createImage(String.valueOf(image.getIdentifier()),
                imageStream, image.getMetaData(), true, response)) {
            LOG.error("failed to store image: " + response);
            throw new IllegalStateException("failed to store image");
        }
    }

    protected Node loadEntityNode(long entityId) {
        ResourceIterable<Node> nodes =
                graph.findNodesByLabelAndProperty(GNodeType.ENTITY,
                        GEntity.PROP_IDENTIFIER, entityId);
        if (nodes != null && nodes.iterator().hasNext()) {
            return nodes.iterator().next();
        }
        return null;
    }

    protected Node loadImageNode(long imageId) {
        ResourceIterable<Node> nodes =
                graph.findNodesByLabelAndProperty(GNodeType.IMAGE,
                        GImage.PROP_IDENTIFIER, imageId);
        if (nodes != null && nodes.iterator().hasNext()) {
            return nodes.iterator().next();
        }
        return null;
    }

    protected GEntity loadEntity(long entityId, boolean createIfNotExisting) {
        // try to load entity from node
        Node entityNode = loadEntityNode(entityId);
        if (entityNode != null) {
            return GEntity.loadFromNode(entityNode);
        }

        // create entity lazy if wished
        if (createIfNotExisting) {
            return new GEntity(graph, entityId);
        }

        return null;
    }

    ///////////////////// COMMAND HANDLERS ///////////////////////

    public void createImage(CreateImageParameterContainer parameters) {
        long imageId = parameters.getImageInfo().getIdentifier();

        if (loadImageNode(imageId) != null) {
            throw ExceptionFactory.usageImageIdentifierInUse(imageId);
        }
        storeImage(parameters.getImageInfo(), parameters.getImageStream());

        // load entity
        GEntity entity = loadEntity(parameters.getEntityId(), true);

        // create image
        GImage image =
                new GImage(graph, "" + parameters.getEntityId(),
                        parameters.getImageInfo());

        // add image
        entity.addImage(image, false);
    }

    public GalleryInfo readImagesOfEntity(
            long entityId,
            int start,
            int numImages) {
        try (Transaction tx = graph.beginTx()) {
            GEntity entity = loadEntity(entityId, false);
            if (entity != null) {
                return entity.readAllImages(start, numImages);
            }

            return new GalleryInfo();
        }
    }

    /////////// INTERFACE METHODS //////////////

    public boolean createImage(
            long entityId,
            ImageInfo imageInfo,
            InputStream imageStream) {
        // TODO stack command
        //        CreateImageParameterContainer parameters =
        //                new CreateImageParameterContainer(entityId, imageInfo,
        //                        imageStream);
        //        return pendingCommands.offer(new CreateImageCommand(this, parameters));

        try (Transaction tx = graph.beginTx()) {
            createImage(new CreateImageParameterContainer(entityId, imageInfo,
                    imageStream));
            tx.success();
            return true;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        // get configuration path
        String configPath;
        if (args.length > 0) {
            configPath = args[0];
        } else {
            configPath = DEFAULT_CONFIG_PATH;
            LOG.info("using default configuration file path \""
                    + DEFAULT_CONFIG_PATH + "\"");
        }

        // load server configuration
        ImageGalleryServerConfig config =
                new ImageGalleryServerConfig(configPath);
        if (!config.isLoaded()) {
            LOG.error("failed to load configuration");
            return;
        }

        new ImageGalleryServer(config);
    }
}
