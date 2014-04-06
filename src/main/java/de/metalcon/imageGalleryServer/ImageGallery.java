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
import de.metalcon.imageGalleryServer.nodes.EntityNode;
import de.metalcon.imageGalleryServer.nodes.GalleryNode;
import de.metalcon.imageGalleryServer.schema.AuthorType;
import de.metalcon.imageGalleryServer.schema.GalleryType;
import de.metalcon.imageGalleryServer.schema.Properties;

public class ImageGallery {

    private static final Object NO_ID = new Object();

    protected Graph graph;

    protected HashMap<Long, Vertex> users;

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
        users = new HashMap<Long, Vertex>();
        galleries = new HashMap<Long, Vertex>();
        images = new HashMap<Long, Vertex>();

        if (!overwrite) {
            // load indices
            EntityNode entity;
            for (Vertex vertex : graph.getVertices()) {
                entity = new EntityNode(vertex);
                if (entity.isValid()) {
                    users.put(entity.getIdentifier(), vertex);
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

    protected Vertex getEntity(long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }

        // create entity if not existing
        Vertex entity = graph.addVertex(id);
        entity.setProperty(Properties.Entity.TYPE, Types.ENTITY);
        entity.setProperty(Properties.Entity.IDENTIFIER, id);
        createGeneratedGalleries(entity);

        users.put(id, entity);

        System.out.println("entity " + id + " created");
        return entity;
    }

    protected void createGeneratedGalleries(Vertex entity) {
        Vertex genGallery = graph.addVertex(NO_ID);
        entity.addEdge(Labels.Entity.Gallery.ALL, genGallery);

        genGallery = graph.addVertex(NO_ID);
        entity.addEdge(Labels.Entity.Gallery.NEWS_FEED, genGallery);

        genGallery = graph.addVertex(NO_ID);
        entity.addEdge(Labels.Entity.Gallery.WIKI, genGallery);

        genGallery = graph.addVertex(NO_ID);
        entity.addEdge(Labels.Entity.Gallery.TAGGED, genGallery);
    }

    protected void createAuthors(Vertex gallery) {
        Vertex author = graph.addVertex(NO_ID);
        gallery.addEdge(Labels.Gallery.Author.OWN, author);

        author = graph.addVertex(NO_ID);
        gallery.addEdge(Labels.Gallery.Author.FOREIGN, author);
        System.out.println("author nodes created for gallery "
                + EntityNode.getIdentifier(gallery));
    }

    protected Vertex getGallery(long entityId, long galleryId) {
        Vertex entity = getEntity(entityId);
        if (galleries.containsKey(galleryId)) {
            return galleries.get(galleryId);
        }

        // create gallery if not existing
        Vertex gallery = graph.addVertex(galleryId);
        gallery.setProperty(Properties.Gallery.TYPE, Types.GALLERY);
        gallery.setProperty(Properties.Gallery.IDENTIFIER, galleryId);
        createAuthors(gallery);
        entity.addEdge(Labels.Entity.Gallery.USER, gallery);

        galleries.put(galleryId, gallery);

        System.out.println("gallery " + galleryId + " created @ entity "
                + entityId);
        return gallery;
    }

    protected Vertex getImage(long id) {
        if (images.containsKey(id)) {
            return images.get(id);
        }
        // FIMXE
        return graph.addVertex(id);
        //        throw new IllegalArgumentException("image is not existing");
    }

    protected void addForeignImageToGallery(Vertex gallery, Vertex image) {
        Vertex otherImages = GalleryNode.getNodeForeignImages(gallery);
        otherImages.addEdge(Labels.Gallery.IMAGE, image);
    }

    public void addImageToGallery(long imageId, long entityId, long galleryId) {
        Vertex image = getImage(imageId);
        Vertex gallery = getGallery(entityId, galleryId);
        Vertex entity = GalleryNode.getOwner(gallery);
        Vertex genGalleryAll = EntityNode.getGenericGalleryAll(entity);

        addForeignImageToGallery(genGalleryAll, image);
        addForeignImageToGallery(gallery, image);
    }

    public void createImage(long entityId, long imgId, InputStream imgData) {
        if (images.containsKey(imgId)) {
            throw new IllegalArgumentException("");
        }
        if (!users.containsKey(entityId)) {
            // TODO create user
        }
        EntityNode entity = new EntityNode(users.get(entityId));

        // create image
        Vertex image = createImage(imgId);

        // link image
        entity.addImage(image, GalleryType.ALL, AuthorType.OWN);
    }

    public static void main(String[] args) throws ServiceOverloadedException {
        ImageGallery gallery = new ImageGallery("/tmp/gallery", true);

        Muid muidUser = Muid.create(MuidType.USER);
        Muid muidGallery = Muid.create(MuidType.INSTRUMENT);
        Muid muidImage = Muid.create(MuidType.GENRE);
        gallery.addImageToGallery(muidImage.getValue(), muidUser.getValue(),
                muidGallery.getValue());

        gallery.graph.shutdown();
    }
}
