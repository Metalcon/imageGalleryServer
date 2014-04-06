package de.metalcon.imageGalleryServer.nodes;

import com.tinkerpop.blueprints.Vertex;

import de.metalcon.imageGalleryServer.GraphNavigator;
import de.metalcon.imageGalleryServer.Image;
import de.metalcon.imageGalleryServer.schema.ImageType;

public class ImageNode extends IndexedNode {

    protected EntityNode owner;

    protected GalleryNode gallery;

    public ImageNode(
            GalleryNode gallery,
            Vertex vertex) {
        super(vertex);
        owner = null;
        this.gallery = gallery;
    }

    public ImageNode(
            Vertex vertex) {
        super(vertex);
        Vertex authorVertex =
                GraphNavigator.backward(vertex, ImageType.UPLOAD.getLabel());
        Vertex galleryVertex = GraphNavigator.backward(authorVertex);
        Vertex entityVertex = GraphNavigator.backward(galleryVertex);
        owner = new EntityNode(entityVertex);
        gallery = null;
    }

    public EntityNode getOwner() {
        return gallery.getOwner();
    }

    // TODO remove
    public static Image loadImage(Vertex vertex) {
        ImageNode image = new ImageNode(vertex);
        return new Image("id:\"" + image.getIdentifier() + "\"", null);
    }

}
