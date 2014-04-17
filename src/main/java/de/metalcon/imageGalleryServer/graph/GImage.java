package de.metalcon.imageGalleryServer.graph;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import de.metalcon.imageGalleryServer.api.ImageInfo;

/**
 * gallery image owned by an entity<br>
 * may be part of a gallery
 * 
 * @author sebschlicht
 * 
 */
public class GImage extends GNode {

    protected static final String PROP_TIMESTAMP = "timestamp";

    protected static final String PROP_URL_SOURCE = "url_source";

    protected static final String PROP_URL_LINK = "url_link";

    protected static final String PROP_TITLE = "title";

    /**
     * create gallery entity
     * 
     * @param graph
     *            graph holding the gallery
     * @param imageInfo
     *            image information
     */
    public GImage(
            GraphDatabaseService graph,
            String url,
            ImageInfo imageInfo) {
        super(graph, GNodeType.IMAGE, imageInfo.getIdentifier());

        node.setProperty(PROP_TIMESTAMP, imageInfo.getTimestamp());
        node.setProperty(PROP_URL_SOURCE, url);
        if (imageInfo.getUrlLink() != null) {
            node.setProperty(PROP_URL_LINK, imageInfo.getUrlLink());
        }
        if (imageInfo.getTitle() != null) {
            node.setProperty(PROP_TITLE, imageInfo.getTitle());
        }
    }

    private GImage(
            Node node) {
        super(node);
    }

    /**
     * @return timestamp of image upload
     */
    public long getTimestamp() {
        return (long) node.getProperty(PROP_TIMESTAMP);
    }

    /**
     * @return URL to plain image
     */
    public String getUrlSource() {
        return (String) node.getProperty(PROP_URL_SOURCE);
    }

    /**
     * @return URL image refers to<br>
     *         (optional, may be <b>null</b>)
     */
    public String getUrlLink() {
        if (node.hasProperty(PROP_URL_LINK)) {
            return (String) node.getProperty(PROP_URL_LINK);
        } else {
            return null;
        }
    }

    /**
     * @return image title<br>
     *         (optional, may be <b>null</b>)
     */
    public String getTitle() {
        if (node.hasProperty(PROP_TITLE)) {
            return (String) node.getProperty(PROP_TITLE);
        } else {
            return null;
        }
    }

    public static GImage loadFromNode(Node imageNode) {
        return new GImage(imageNode);
    }

}
