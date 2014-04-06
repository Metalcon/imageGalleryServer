package de.metalcon.imageGalleryServer;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;

/**
 * helper class to navigate along a graph
 * 
 * @author sebschlicht
 * 
 */
public class GraphNavigator {

    /**
     * walk forward along an edge
     * 
     * @param start
     *            vertex to start from
     * @param edgeLabel
     *            edge label
     * @return vertex first vertex path leads to
     */
    public static Vertex next(Vertex start, String edgeLabel) {
        return start.getVertices(Direction.OUT, edgeLabel).iterator().next();
    }

    /**
     * walk backward along an edge
     * 
     * @param start
     *            vertex to start from
     * @param edgeLabel
     *            edge label
     * @return vertex first vertex path leads from
     */
    public static Vertex previous(Vertex start, String edgeLabel) {
        return start.getVertices(Direction.IN, edgeLabel).iterator().next();
    }

}
