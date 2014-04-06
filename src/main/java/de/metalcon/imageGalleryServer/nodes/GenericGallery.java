package de.metalcon.imageGalleryServer.nodes;

import de.metalcon.imageGalleryServer.GraphNavigator;
import de.metalcon.imageGalleryServer.schema.GalleryType;

/**
 * generic gallery owned by an entity
 * 
 * @author sebschlicht
 * 
 */
public class GenericGallery extends GalleryNode {

    /**
     * load generic gallery
     * 
     * @param owner
     *            gallery owner
     * @param type
     *            gallery type
     */
    public GenericGallery(
            EntityNode owner,
            GalleryType type) {
        super(owner);
        vertex = GraphNavigator.forward(owner.getVertex(), type.getLabel());
    }

}
