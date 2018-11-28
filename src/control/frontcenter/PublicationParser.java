package control.frontcenter;

import exception.parsingexception.PublicationParsingException;
import model.Borrower.Borrower;
import model.publication.Publication;
import model.publication.PublicationItems;
import observer.ItemReserver;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

// Parser for resources in XML format
public class PublicationParser implements LibraryLogParser {
    private String sourceFileName;
    private static PublicationParser instance = new PublicationParser("Items.xml");


    // EFFECTS: constructs parser for data in given source file
    private PublicationParser(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public static PublicationParser getInstance() {
        return instance;
    }

    @Override
    public void parseItems() throws PublicationParsingException, IOException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            PublicationHandler ph = new PublicationHandler();
            saxParser.parse(sourceFileName, ph);
        } catch (ParserConfigurationException e) {
            PublicationParsingException publicationParsingException = new PublicationParsingException("Parser configuration error");
            publicationParsingException.initCause(e);
            throw publicationParsingException;
        } catch (SAXException e) {
            PublicationParsingException publicationParsingException = new PublicationParsingException("SAX parser error");
            publicationParsingException.initCause(e);
            throw publicationParsingException;
        } catch(NumberFormatException e) {
            PublicationParsingException publicationParsingException = new PublicationParsingException("XML format error");
            publicationParsingException.initCause(e);
            throw publicationParsingException;
        }
        if (PublicationItems.getPublicationHashMap().isEmpty()) {
            throw new PublicationParsingException("empty map!!!!Bad!!!");
        }
    }

    public void createXML() throws Exception{
        SAXTransformerFactory tff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        TransformerHandler handler = tff.newTransformerHandler();
        Transformer tr = handler.getTransformer();
        tr.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        File file = new File("Items.xml");
        if(!file.exists()){
            file.createNewFile();
        }
        Result result = new StreamResult(new FileOutputStream(file));
        handler.setResult(result);

        handler.startDocument();
        AttributesImpl atts = new AttributesImpl();
        handler.startElement("", "", "publications", atts);
        Iterator iter = PublicationItems.getPublicationHashMap().entrySet().iterator();
        while (iter.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter.next();
            ArrayList<Publication> itemlist = (ArrayList<Publication>) entry.getValue();
            if(itemlist.size()!=0) {
                for (Publication a : itemlist) {
                    atts.clear();
                    atts.addAttribute("", "", "isbn", "", a.getIsbn());
                    handler.startElement("", "", "item", atts);

                    //isbook

                        atts.clear();
                        handler.startElement("", "", "isbook", atts);
                        if(a.isIsbook()) {
                            handler.characters("true".toCharArray(), 0, "true".length());
                        }
                        if(!a.isIsbook()){
                            handler.characters("false".toCharArray(), 0, "false".length());

                        }
                        handler.endElement("", "", "isbook");

                    //name
                    if(a.getName()!=null && !a.getName().trim().equals("")){
                        atts.clear();
                        handler.startElement("", "", "name", atts);
                        handler.characters(a.getName().toCharArray(), 0, a.getName().length());
                        handler.endElement("", "", "name");
                    }
                    //isbn
                    if(a.getIsbn()!=null && !a.getIsbn().trim().equals("")){
                        atts.clear();
                        handler.startElement("", "", "isbn", atts);
                        handler.characters(a.getIsbn().toCharArray(), 0, a.getIsbn().length());
                        handler.endElement("", "", "isbn");
                    }
                    //author
                    if(a.getAuthorName()!=null && !a.getAuthorName().trim().equals("")){
                        atts.clear();
                        handler.startElement("", "", "authorname", atts);
                        handler.characters(a.getAuthorName().toCharArray(), 0, a.getAuthorName().length());
                        handler.endElement("", "", "authorname");
                    }
                    //sharesinfo
                    atts.clear();
                    handler.startElement("","","sharesinfo",atts);
                        //multiplicity
                        atts.clear();
                        handler.startElement("", "", "multiplicity", atts);
                        handler.characters(Integer.toString(a.getShares().getMultiplicity()).toCharArray(), 0, Integer.toString(a.getShares().getMultiplicity()).length());
                        handler.endElement("", "", "multiplicity");
                        //remaining
                        atts.clear();
                        handler.startElement("", "", "remaining", atts);
                        handler.characters(Integer.toString(a.getShares().getRemaining()).toCharArray(), 0, Integer.toString(a.getShares().getRemaining()).length());
                        handler.endElement("", "", "remaining");
                    handler.endElement("", "", "sharesinfo");
                    //currentborrowerlist
                    atts.clear();
                    handler.startElement("","","borrowers",atts);
                        for(Borrower b:a.getCurrentborrowers()){
                            //currentborrowername
                            atts.clear();
                            handler.startElement("","","currentborrowername",atts);
                            handler.characters(b.getNameofbrwr().toCharArray(), 0,b.getNameofbrwr().length());
                            handler.endElement("", "", "currentborrowername");
                        }
                    handler.endElement("", "", "borrowers");
                    //reservers
                    atts.clear();
                    handler.startElement("","","reservers",atts);
                    for(ItemReserver b:a.getReservers()){
                         //reservername
                        atts.clear();
                        Borrower c=(Borrower)b;
                        handler.startElement("","","reservername",atts);
                        handler.characters(c.getNameofbrwr().toCharArray(), 0,c.getNameofbrwr().length());
                        handler.endElement("", "", "reservername");
                    }
                    handler.endElement("", "", "reservers");
                    handler.endElement("", "", "item");
                }
            }
        }
        handler.endElement("", "", "publications");
        handler.endDocument();
    }
}
