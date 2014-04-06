package de.metalcon.imageGalleryServer.tagging;

import java.util.LinkedList;
import java.util.List;

public class TagInformation {

    protected List<TagEntry> entries;

    public TagInformation() {
        entries = new LinkedList<TagEntry>();
    }

}
