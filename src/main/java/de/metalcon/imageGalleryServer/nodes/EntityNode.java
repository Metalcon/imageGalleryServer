package de.metalcon.imageGalleryServer.nodes;

import java.util.HashMap;
import java.util.Map;

import com.tinkerpop.blueprints.Vertex;

import de.metalcon.imageGalleryServer.schema.GalleryType;
import de.metalcon.imageGalleryServer.schema.NodeType;
import de.metalcon.imageGalleryServer.schema.Properties;

/**
 * entity
 * 
 * @author sebschlicht
 * 
 */
public class EntityNode extends IndexedNode {

    /**
     * generated galleries owned by the entity
     */
    protected Map<GalleryType, GalleryNode> generatedGalleries;

    /**
     * load entity
     * 
     * @param vertex
     *            vertex to load from
     */
    public EntityNode(
            Vertex vertex) {
        super(vertex);
        generatedGalleries = new HashMap<GalleryType, GalleryNode>();
    }

    /**
     * check if this node is an entity
     * 
     * @return true - if it is an entity<br>
     *         false - otherwise
     */
    public boolean isValid() {
        return NodeType.ENTITY.getIdentifier().equals(
                vertex.<String> getProperty(Properties.Entity.TYPE));
    }

    /**
     * get generated gallery owned by this entity
     * 
     * @param type
     *            gallery type
     * @return gallery of the entity having the type specified
     */
    public GalleryNode getGeneratedGallery(GalleryType type) {
        if (!generatedGalleries.containsKey(type)) {
            generatedGalleries.put(type, new GenericGallery(this, type));
        }
        return generatedGalleries.get(type);
    }

}
