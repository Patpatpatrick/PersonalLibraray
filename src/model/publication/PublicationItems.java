package model.publication;

import model.publication.Publication;

import java.util.ArrayList;
import java.util.HashMap;

public class PublicationItems {

    private static HashMap<String,ArrayList<Publication>> PublicationHashMap =new HashMap<>();

    public static HashMap<String, ArrayList<Publication>> getPublicationHashMap() {
        return PublicationHashMap;
    }


}
