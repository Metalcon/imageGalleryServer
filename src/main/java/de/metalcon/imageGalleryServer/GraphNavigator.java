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

    protected static final String[] ANY_LABEL = new String[0];

    /**
     * walk forward along an edge
     * 
     * @param start
     *            vertex to start from
     * @param edgeLabel
     *            edge label
     * @return first vertex the path leads to
     */
    public static Vertex forward(Vertex start, String edgeLabel) {
        return start.getVertices(Direction.OUT, edgeLabel).iterator().next();
    }

    /**
     * walk backward along an edge
     * 
     * @param start
     *            vertex to start from
     * @param edgeLabel
     *            edge label
     * @return first vertex the path leads from
     */
    public static Vertex backward(Vertex start, String edgeLabel) {
        return start.getVertices(Direction.IN, edgeLabel).iterator().next();
    }

    /**
     * walk backward along any edge
     * 
     * @param start
     *            vertex to start from
     * @return first vertex a path leads from
     */
    public static Vertex backward(Vertex start) {
        return start.getVertices(Direction.IN, ANY_LABEL).iterator().next();
    }

}
