package de.metalcon.imageGalleryServer.tagging;

import java.awt.Point;

public class TagEntry {

    protected long entityId;

    protected Point coordinate;

    public TagEntry(
            long entityId) {
        this(entityId, null);
    }

    public TagEntry(
            long entityId,
            Point coordinate) {
        this.entityId = entityId;
        this.coordinate = coordinate;
    }

}
