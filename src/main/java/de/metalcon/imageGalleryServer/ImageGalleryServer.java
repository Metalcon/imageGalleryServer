package de.metalcon.imageGalleryServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.Schema;

import de.metalcon.imageGalleryServer.api.GalleryInfo;
import de.metalcon.imageGalleryServer.api.ImageInfo;
import de.metalcon.imageGalleryServer.commands.GalleryCommand;
import de.metalcon.imageGalleryServer.commands.parameters.CreateImageParameterContainer;
import de.metalcon.imageGalleryServer.exception.ExceptionFactory;
import de.metalcon.imageGalleryServer.graph.GEntity;
import de.metalcon.imageGalleryServer.graph.GImage;
import de.metalcon.imageGalleryServer.graph.GNodeType;
import de.metalcon.imageGalleryServer.graph.traversal.ImageTraversalAll;
import de.metalcon.imageStorageServer.ImageStorageServer;
import de.metalcon.imageStorageServer.protocol.create.CreateResponse;

public class ImageGalleryServer implements ImageGallery {

    protected ImageStorageServer storageServer;

    protected GraphDatabaseService graph;

    protected BlockingQueue<GalleryCommand<?>> pendingCommands;

    public ImageGalleryServer(
            ImageGalleryServerConfig config) {
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
            System.out.println("indices online");

            ImageTraversalAll.init(graph);
            System.out.println("filtering engines loaded");

            tx.success();
            tx.close();
        }
        System.out.println("database online");
    }

    /////////////// HELPER METHODS /////////////////////

    protected void storeImage(ImageInfo image, InputStream imageStream) {
        CreateResponse response = new CreateResponse();
        if (!storageServer.createImage(String.valueOf(image.getIdentifier()),
                imageStream, image.getMetaData(), true, response)) {
            System.err.println("failed to store image: " + response);
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
        GImage image = new GImage(graph, parameters.getImageInfo());

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
        String configPath = "src/main/resources/test.config";
        ImageGalleryServer gallery =
                new ImageGalleryServer(new ImageGalleryServerConfig(configPath));

        InputStream imageStream =
                new FileInputStream("src/main/resources/test.png");
        ImageInfo imageInfo =
                new ImageInfo(System.currentTimeMillis(), 6,
                        "http://google.de/", null, null);

        gallery.createImage(1, imageInfo, imageStream);

        GalleryInfo result = gallery.readImagesOfEntity(1, 0, 100);
        System.out.println("num images: " + result.getSize());
        for (ImageInfo ii : result.getImagesLoaded()) {
            System.out.println(ii.getIdentifier() + ": " + ii.getUrlSource()
                    + " [" + ii.getTimestamp() + "]");
        }
        System.out.println("image created");
    }

}
