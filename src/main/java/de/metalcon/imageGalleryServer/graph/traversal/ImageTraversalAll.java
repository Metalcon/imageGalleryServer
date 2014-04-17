package de.metalcon.imageGalleryServer.graph.traversal;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.traversal.TraversalDescription;

import de.metalcon.imageGalleryServer.graph.traversal.evaluators.ImageEvaluatorAll;

public class ImageTraversalAll {

    protected static TraversalDescription TRAVERSAL_DESCRIPTION;

    public static void init(GraphDatabaseService graph) {
        // breadth search
        TRAVERSAL_DESCRIPTION = graph.traversalDescription().breadthFirst();

        // all images
        TRAVERSAL_DESCRIPTION =
                TRAVERSAL_DESCRIPTION.evaluator(new ImageEvaluatorAll());
    }

    public static TraversalDescription getTraversalDescription() {
        return TRAVERSAL_DESCRIPTION;
    }

}
