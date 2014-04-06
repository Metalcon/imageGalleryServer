package de.metalcon.imageGalleryServer.nodes;

import com.tinkerpop.blueprints.Vertex;

import de.metalcon.imageGalleryServer.Image;

public class ImageNode extends IndexedNode {

    public ImageNode(
            Vertex vertex) {
        super(vertex);
    }

    // TODO remove
    public static Image loadImage(Vertex vertex) {
        ImageNode image = new ImageNode(vertex);
        return new Image("id:\"" + image.getIdentifier() + "\"", null);
    }

}
