package de.metalcon.imageGalleryServer;

public class Labels {

    public class Entity {

        public class Gallery {

            public static final String ALL = "genGalleryAll";

            public static final String NEWS_FEED = "genGalleryNewsFeed";

            public static final String WIKI = "genGalleryWiki";

            public static final String TAGGED = "genGalleryTagged";

            public static final String USER = "userGallery";
        }
    }

    public class Gallery {

        public static final String IMAGE = "image";

        public class Author {

            public static final String OWN = "ownImages";

            public static final String FOREIGN = "otherImages";
        }
    }
}
