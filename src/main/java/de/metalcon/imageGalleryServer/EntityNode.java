package de.metalcon.imageGalleryServer;

import com.tinkerpop.blueprints.Vertex;

public class EntityNode {

    public static boolean isEntityNode(Vertex vertex) {
        Integer type = vertex.<Integer> getProperty(Properties.Entity.TYPE);
        return type != null && type == Types.ENTITY;
    }

    public static long getIdentifier(Vertex vertex) {
        return vertex.<Long> getProperty(Properties.Entity.IDENTIFIER);
    }

    public static Vertex getGenericGalleryAll(Vertex entity) {
        return GraphNavigator.next(entity, Labels.Entity.Gallery.ALL);
    }

}
