package de.metalcon.imageGalleryServer.graph.traversal.comparators;

import java.util.Comparator;

import org.neo4j.graphdb.Relationship;

import de.metalcon.imageGalleryServer.graph.GEntity;

public class ImageComparator implements Comparator<Relationship> {

    @Override
    public int compare(Relationship imgRel1, Relationship imgRel2) {
        long tsImg1 = (long) imgRel1.getProperty(GEntity.AUTHOR_PROP_TIMESTAMP);
        long tsImg2 = (long) imgRel2.getProperty(GEntity.AUTHOR_PROP_TIMESTAMP);

        if (tsImg1 == tsImg2) {
            return 0;
        } else if (tsImg1 < tsImg2) {
            return -1;
        } else {
            return 1;
        }
    }

}
