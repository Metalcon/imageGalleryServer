package de.metalcon.imageGalleryServer.graph.traversal.evaluators;

import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;

import de.metalcon.imageGalleryServer.graph.GNodeType;

/**
 * path evaluator to get all images of an entity
 * 
 * @author sebschlicht
 * 
 */
public class ImageEvaluatorAll implements Evaluator {

    @Override
    public Evaluation evaluate(Path path) {
        // loose images
        if (path.length() == 1 && path.endNode().hasLabel(GNodeType.IMAGE)) {
            return Evaluation.INCLUDE_AND_CONTINUE;
        }

        // images in galleries
        if (path.length() == 2 && path.endNode().hasLabel(GNodeType.IMAGE)) {
            return Evaluation.INCLUDE_AND_CONTINUE;
        }

        return Evaluation.EXCLUDE_AND_CONTINUE;
    }

}
