package de.metalcon.imageGalleryServer.nodes;

import com.tinkerpop.blueprints.Vertex;

import de.metalcon.imageGalleryServer.schema.Properties;

/**
 * basic node that can be indexed via identifier
 * 
 * @author sebschlicht
 * 
 */
public abstract class IndexedNode extends WrappedNode {

    /**
     * unique identifier for indexing
     */
    protected Long identifier;

    /**
     * create basic indexable node
     * 
     * @param vertex
     *            vertex to load from
     */
    public IndexedNode(
            Vertex vertex) {
        super(vertex);
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
