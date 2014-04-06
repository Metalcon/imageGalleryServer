package de.metalcon.imageGalleryServer.nodes;

import com.tinkerpop.blueprints.Vertex;

/**
 * basic node wrapped around a vertex
 * 
 * @author sebschlicht
 * 
 */
public abstract class WrappedNode {

    /**
     * vertex representing the node
     */
    protected Vertex vertex;

    /**
     * create wrapped node
     * 
     * @param vertex
     *            vertex to load from
     */
    public WrappedNode(
            Vertex vertex) {
        this.vertex = vertex;
    }

    /**
     * @return vertex representing the node
     */
    public Vertex getVertex() {
        return vertex;
    }

}
