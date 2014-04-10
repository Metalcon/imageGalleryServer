package de.metalcon.imageGalleryServer.graph;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.traversal.TraversalDescription;

import de.metalcon.imageGalleryServer.GalleryInfo;
import de.metalcon.imageGalleryServer.ImageInfo;
import de.metalcon.imageGalleryServer.graph.relationshipTypes.ImageType;
import de.metalcon.imageGalleryServer.graph.traversal.ImageTraversalAll;
import de.metalcon.imageGalleryServer.graph.traversal.comparators.ImageComparator;

/**
 * gallery entity owning galleries and images
 * 
 * @author sebschlicht
 * 
 */
public class GEntity extends GNode {

    public static final String AUTHOR_PROP_TIMESTAMP = "timestamp";

    protected static TraversalDescription TD_ALL_IMAGES = ImageTraversalAll
            .getTraversalDescription();

    protected static final Comparator<Relationship> COMP_IMAGES =
            new ImageComparator();

    /**
     * create gallery entity
     * 
     * @param graph
     *            graph holding the gallery
     * @param identifier
     *            identifier for the new entity
     */
    public GEntity(
            GraphDatabaseService graph,
            long identifier) {
        super(graph, GNodeType.ENTITY, identifier);
    }

    /**
     * load entity from node
     * 
     * @param entityNode
     *            node representing an entity
     */
    private GEntity(
            Node entityNode) {
        super(entityNode);
    }

    /**
     * add an image for the entity
     * 
     * @param image
     *            image to be added
     * @param checkIfExisting
     *            check if the image is already linked if set to <b>true</b>
     */
    public void addImage(GImage image, boolean checkIfExisting) {
        if (checkIfExisting) {
            // TODO
        }

        Relationship edge =
                node.createRelationshipTo(image.getNode(), ImageType.UPLOAD);
        edge.setProperty(AUTHOR_PROP_TIMESTAMP, image.getTimestamp());
    }

    protected GalleryInfo pageImages(
            List<Relationship> imageItems,
            int start,
            int numImages) {
        List<ImageInfo> imagesLoaded = new LinkedList<ImageInfo>();
        int numTotalImages = imageItems.size();

        int crrItem = 0;
        int lastItem = (numImages != 0) ? (start + numImages) : numTotalImages;
        GImage image;

        for (Relationship item : imageItems) {
            // skip images until lower paging border
            if (++crrItem <= start) {
                continue;
            }

            image = GImage.loadFromNode(item.getEndNode());
            imagesLoaded.add(new ImageInfo(image.getTimestamp(), image
                    .getIdentifier(), image.getUrlSource(), image.getUrlLink(),
                    image.getTitle()));

            if (crrItem == lastItem) {
                break;
            }
        }

        return new GalleryInfo(numTotalImages, imagesLoaded);
    }

    public GalleryInfo readAllImages(int start, int numImages) {
        List<Relationship> relationships = new LinkedList<Relationship>();
        for (Path path : TD_ALL_IMAGES.traverse(node)) {
            relationships.add(path.lastRelationship());
        }
        Collections.sort(relationships, COMP_IMAGES);

        return pageImages(relationships, start, numImages);
    }

    /**
     * load entity from an existing entity node
     * 
     * @param entityNode
     *            node representing an entity
     * @return entity loaded from node specified
     */
    public static GEntity loadFromNode(Node entityNode) {
        return new GEntity(entityNode);
    }

}
