package de.metalcon.imageGalleryServer.nodes;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;

import de.metalcon.imageGalleryServer.GraphNavigator;
import de.metalcon.imageGalleryServer.schema.AuthorType;
import de.metalcon.imageGalleryServer.schema.ImageType;

/**
 * basic gallery owned by an entity
 * 
 * @author sebschlicht
 * 
 */
public abstract class GalleryNode extends WrappedNode {

    /**
     * gallery owner
     */
    protected EntityNode owner;

    /**
     * proxy node for images uploaded by gallery owner
     */
    protected Vertex ownImages;

    /**
     * proxy node for images uploaded by an other entity than gallery owner
     */
    protected Vertex foreignImages;

    /**
     * load basic gallery<br>
     * leaves vertex <b>null</b>
     * 
     * @param owner
     *            gallery owner
     */
    public GalleryNode(
            EntityNode owner) {
        super(null);
        this.owner = owner;
    }

    protected Vertex getOwnImages() {
        if (ownImages == null) {
            ownImages = GraphNavigator.next(vertex, AuthorType.OWN.getLabel());
        }
        return ownImages;
    }

    protected Vertex getForeignImages() {
        if (foreignImages == null) {
            foreignImages =
                    GraphNavigator.next(vertex, AuthorType.FOREIGN.getLabel());
        }
        return foreignImages;
    }

    /**
     * add an image to this gallery
     * 
     * @param image
     *            image vertex
     * @param authorType
     *            author type
     * @param checkIfLinked
     *            does not add image twice if already linked to this gallery if
     *            set to true
     */
    public void addImage(
            Vertex image,
            AuthorType authorType,
            boolean checkIfLinked) {
        Vertex gallery;
        String label;

        if (authorType == AuthorType.OWN) {
            gallery = getOwnImages();
            label = ImageType.UPLOAD.getLabel();

            // loop through linked galleries of owner to search for this gallery
            if (checkIfLinked) {
                for (Vertex galleryLinkedTo : image.getVertices(Direction.IN,
                        label)) {
                    if (gallery.equals(galleryLinkedTo)) {
                        return;
                    }
                }
            }
        } else {
            gallery = getForeignImages();
            label = ImageType.LINK.getLabel();

            // loop through gallery images to search for image
            if (checkIfLinked) {
                for (Vertex galleryImage : gallery.getVertices(Direction.OUT,
                        label)) {
                    if (image.equals(galleryImage)) {
                        return;
                    }
                }
            }
        }

        gallery.addEdge(label, image);
    }
}
