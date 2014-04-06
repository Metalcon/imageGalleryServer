package de.metalcon.imageGalleryServer.nodes;

import com.tinkerpop.blueprints.Vertex;

import de.metalcon.imageGalleryServer.schema.Properties;

public class ManualGalleryNode extends GalleryNode {

    /**
     * unique identifier for indexing
     */
    protected Long identifier;

    public ManualGalleryNode(
            EntityNode owner,
            Vertex vertex) {
        super(owner);
        this.vertex = vertex;
    }

    /**
     * @return unique identifier for indexing
     */
    public long getIdentifier() {
        if (identifier == null) {
            identifier = vertex.<Long> getProperty(Properties.IDENTIFIER);
        }
        return identifier;
    }

}
