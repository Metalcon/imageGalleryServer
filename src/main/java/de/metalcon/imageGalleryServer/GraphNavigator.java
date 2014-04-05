package de.metalcon.imageGalleryServer;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;

public class GraphNavigator {

    public static Vertex next(Vertex start, String edgeLabel) {
        return start.getVertices(Direction.OUT, edgeLabel).iterator().next();
    }

    public static Vertex previous(Vertex start, String edgeLabel) {
        return start.getVertices(Direction.IN, edgeLabel).iterator().next();
    }

}
