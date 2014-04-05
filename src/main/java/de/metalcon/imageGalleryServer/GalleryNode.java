package de.metalcon.imageGalleryServer;

import com.tinkerpop.blueprints.Vertex;

public class GalleryNode {

    public static Vertex getOwner(Vertex gallery) {
        return GraphNavigator.previous(gallery, Labels.Entity.Gallery.USER);
    }

    public static Vertex getNodeOwnImages(Vertex gallery) {
        return GraphNavigator.next(gallery, Labels.Gallery.Author.OWN);
    }

    public static Vertex getNodeForeignImages(Vertex gallery) {
        return GraphNavigator.next(gallery, Labels.Gallery.Author.FOREIGN);
    }

}
