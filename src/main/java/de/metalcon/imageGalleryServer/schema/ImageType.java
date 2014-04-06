package de.metalcon.imageGalleryServer.schema;

public enum ImageType {

    UPLOAD("upload"),

    LINK("link");

    private String label;

    private ImageType(
            String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
