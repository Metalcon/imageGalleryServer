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
import de.metalcon.imageGalleryServer.nodes.GalleryNode;
import de.metalcon.imageGalleryServer.nodes.ImageNode;
import de.metalcon.imageGalleryServer.nodes.ManualGalleryNode;
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
        createAuthors(genGallery);
        entity.addEdge(GalleryType.ALL.getLabel(), genGallery);

        genGallery = graph.addVertex(NO_ID);
        createAuthors(genGallery);
        entity.addEdge(GalleryType.NEWS_FEED.getLabel(), genGallery);

        genGallery = graph.addVertex(NO_ID);
        createAuthors(genGallery);
        entity.addEdge(GalleryType.WIKI.getLabel(), genGallery);

        genGallery = graph.addVertex(NO_ID);
        createAuthors(genGallery);
        entity.addEdge(GalleryType.TAGGED.getLabel(), genGallery);
    }

    protected Vertex createEntityVertex(long id) {
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

    protected Vertex getEntityVertex(long id) {
        if (entities.containsKey(id)) {
            return entities.get(id);
        }
        return createEntityVertex(id);
    }

    protected Vertex createImageVertex(long id) {
        Vertex image = graph.addVertex(id);
        image.setProperty(Properties.IDENTIFIER, id);

        // add to index
        images.put(id, image);

        System.out.println("created image " + id);
        return image;
    }

    public void createImage(long entityId, long imageId, InputStream imgData) {
        if (images.containsKey(imageId)) {
            throw ExceptionFactory.usageImageIdentifierUsed(imageId);
        }
        EntityNode entity = new EntityNode(getEntityVertex(entityId));

        // create new image
        ImageNode image =
                new ImageNode(entity.getGeneratedGallery(GalleryType.ALL),
                        createImageVertex(imageId));

        // link new image
        entity.addImage(image, GalleryType.ALL, AuthorType.OWN, false);

        // TODO store image
    }

    protected Vertex createGalleryVertex(Vertex entity, long galleryId) {
        Vertex gallery = graph.addVertex(galleryId);
        gallery.setProperty(Properties.IDENTIFIER, galleryId);
        createAuthors(gallery);
        entity.addEdge(GalleryType.MANUAL.getLabel(), gallery);

        // add to index
        galleries.put(galleryId, gallery);

        System.out.println("created gallery " + galleryId);
        return gallery;
    }

    protected Vertex getGalleryVertex(EntityNode entity, long galleryId) {
        if (galleries.containsKey(galleryId)) {
            return galleries.get(galleryId);
        }
        return createGalleryVertex(entity.getVertex(), galleryId);
    }

    public void addImage(long entityId, long galleryId, long imageId) {
        if (!images.containsKey(imageId)) {
            throw ExceptionFactory.usageImageIdentifierUnknown(imageId);
        }
        EntityNode entity = new EntityNode(getEntityVertex(entityId));
        GalleryNode gallery =
                new ManualGalleryNode(entity, getGalleryVertex(entity,
                        galleryId));
        ImageNode image = new ImageNode(gallery, images.get(imageId));

        AuthorType authorType;
        if (entity.equals(image.getOwner())) {
            authorType = AuthorType.OWN;
        } else {
            authorType = AuthorType.FOREIGN;
        }

        // link image
        gallery.addImage(image, authorType, true);
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

        try {
            Muid muidEntity = Muid.create(MuidType.USER);
            Muid muidImage = Muid.create(MuidType.GENRE);
            gallery.createImage(1, 2, null);
            System.out.println(gallery.readImage(2).metaData);
        } finally {
            gallery.graph.shutdown();
        }
    }
}
