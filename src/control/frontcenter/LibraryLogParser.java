package control.frontcenter;

import exception.parsingexception.PublicationParsingException;

import java.io.IOException;

public interface LibraryLogParser {


    void parseItems() throws PublicationParsingException, IOException;
   // void parseBorrowers() throws PublicationParsingException, IOException;
//    HashMap<String,ArrayList<publication>> parseItems() throws PublicationParsingException, IOException;

}