package de.metalcon.imageGalleryServer;

import de.metalcon.imageGalleryServer.api.ImageInfo;

public class LoadedImage implements ImageInfo {

    protected long timestamp;

    protected long identifier;

    protected String urlLink;

    protected String title;

    protected String metaData;

    public LoadedImage(
            long timestamp,
            long identifier,
            String urlLink,
            String title) {
        this.timestamp = timestamp;
        this.identifier = identifier;
        this.urlLink = urlLink;
        this.title = title;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public long getIdentifier() {
        return identifier;
    }

    @Override
    public String getUrlLink() {
        return urlLink;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

}
