package de.metalcon.imageGalleryServer.schema;

public class Properties {

    public static final String IDENTIFIER = "identifier";

    private class Node {

        public static final String TYPE = "type";
    }

    public class Entity extends Node {

        public static final String IDENTIFIER = "identifier";
    }

    public class Gallery extends Node {

        public static final String IDENTIFIER = "identifier";
    }
}
