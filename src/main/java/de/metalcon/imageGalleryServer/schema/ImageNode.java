package de.metalcon.imageGalleryServer.schema;

import com.tinkerpop.blueprints.Vertex;

import de.metalcon.imageGalleryServer.nodes.IndexedNode;

public class ImageNode extends IndexedNode {

    public ImageNode(
            Vertex vertex) {
        super(vertex);
    }

}
