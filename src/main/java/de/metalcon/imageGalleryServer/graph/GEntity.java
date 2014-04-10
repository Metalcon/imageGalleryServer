package de.metalcon.imageGalleryServer.graph;

import org.neo4j.graphdb.GraphDatabaseService;

public class GEntity extends GNode {

    public GEntity(
            GraphDatabaseService graph) {
        super(graph);
    }

}
