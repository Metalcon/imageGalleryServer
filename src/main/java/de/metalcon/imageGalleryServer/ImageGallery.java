package de.metalcon.imageGalleryServer;

import java.io.InputStream;
import java.util.HashMap;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

import de.metalcon.domain.Muid;
import de.metalcon.domain.MuidType;
import de.metalcon.exceptions.ServiceOverloadedException;
import de.metalcon.imageGalleryServer.exceptions.ExceptionFactory;
import de.metalcon.imageGalleryServer.nodes.EntityNode;
import de.metalcon.imageGalleryServer.nodes.ImageNode;
import de.metalcon.imageGalleryServer.schema.AuthorType;
import de.metalcon.imageGalleryServer.schema.GalleryType;
import de.metalcon.imageGalleryServer.schema.NodeType;
import de.metalcon.imageGalleryServer.schema.Properties;

public class ImageGallery {

    private static final Object NO_ID = new Object();

    protected Graph graph;

    protected HashMap<Long, Vertex> entities;

    protected HashMap<Long, Vertex> galleries;

    protected HashMap<Long, Vertex> images;

    public ImageGallery(
            String dbPath) {
        this(dbPath, false);
    }

    public ImageGallery(
            String dbPath,
            boolean overwrite) {
        graph = new Neo4jGraph(dbPath);
        // TODO use built-in indexing
        entities = new HashMap<Long, Vertex>();
        galleries = new HashMap<Long, Vertex>();
        images = new HashMap<Long, Vertex>();

        if (!overwrite) {
            // load indices
            EntityNode entity;
            for (Vertex vertex : graph.getVertices()) {
                entity = new EntityNode(vertex);
                if (entity.isValid()) {
                    entities.put(entity.getIdentifier(), vertex);
                }
            }
        } else {
            // remove edges
            for (Edge edge : graph.getEdges()) {
                graph.removeEdge(edge);
            }
            // remove vertices
            for (Vertex vertex : graph.getVertices()) {
                graph.removeVertex(vertex);
            }
        }
    }

    protected void createAuthors(Vertex gallery) {
        Vertex author = graph.addVertex(NO_ID);
        gallery.addEdge(AuthorType.OWN.getLabel(), author);

        author = graph.addVertex(NO_ID);
        gallery.addEdge(AuthorType.FOREIGN.getLabel(), author);
    }

    protected void createGeneratedGalleries(Vertex entity) {
        Vertex genGallery = graph.addVertex(NO_ID);
        entity.addEdge(GalleryType.ALL.getLabel(), genGallery);
        createAuthors(genGallery);

        genGallery = graph.addVertex(NO_ID);
        entity.addEdge(GalleryType.NEWS_FEED.getLabel(), genGallery);
        createAuthors(genGallery);

        genGallery = graph.addVertex(NO_ID);
        entity.addEdge(GalleryType.WIKI.getLabel(), genGallery);
        createAuthors(genGallery);

        genGallery = graph.addVertex(NO_ID);
        entity.addEdge(GalleryType.TAGGED.getLabel(), genGallery);
        createAuthors(genGallery);
    }

    protected Vertex createEntity(long id) {
        Vertex entity = graph.addVertex(id);
        entity.setProperty(Properties.Entity.TYPE,
                NodeType.ENTITY.getIdentifier());
        entity.setProperty(Properties.IDENTIFIER, id);
        createGeneratedGalleries(entity);

        // add to index
        entities.put(id, entity);

        System.out.println("created entity " + id);
        return entity;
    }

    protected Vertex getEntity(long id) {
        if (entities.containsKey(id)) {
            return entities.get(id);
        }
        return createEntity(id);
    }

    protected Vertex createImage(long id) {
        Vertex image = graph.addVertex(id);
        image.setProperty(Properties.IDENTIFIER, id);

        // add to index
        images.put(id, image);

        System.out.println("created image " + id);
        return image;
    }

    public void createImage(long entityId, long imgId, InputStream imgData) {
        if (images.containsKey(imgId)) {
            throw ExceptionFactory.usageImageIdentifierUsed(imgId);
        }
        EntityNode entity = new EntityNode(getEntity(entityId));

        // create new image
        Vertex image = createImage(imgId);

        // link new image
        entity.addImage(image, GalleryType.ALL, AuthorType.OWN, false);

        // TODO store image
    }

    public Image readImage(long imageId) {
        if (!images.containsKey(imageId)) {
            throw ExceptionFactory.usageImageIdentifierUnknown(imageId);
        }
        // TODO pipe image
        Vertex image = images.get(imageId);
        return ImageNode.loadImage(image);
    }

    public static void main(String[] args) throws ServiceOverloadedException {
        ImageGallery gallery = new ImageGallery("/tmp/gallery", true);

        Muid muidEntity = Muid.create(MuidType.USER);
        Muid muidImage = Muid.create(MuidType.GENRE);
        gallery.createImage(1, 2, null);
        System.out.println(gallery.readImage(2).metaData);

        gallery.graph.shutdown();
    }
}
