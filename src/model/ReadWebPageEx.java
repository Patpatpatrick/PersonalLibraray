package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadWebPageEx {

    public static void main(String[] args) throws MalformedURLException, IOException {

        BufferedReader br = null;

        try {
            String theURL = "https://www.w3schools.com/xml/plant_catalog.xml"; //this can point to any URL
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {

                sb.append(line);
                sb.append(System.lineSeparator());
            }
            stringToDom(sb.toString());
            stringToDom(sb.toString());
//            System.out.println(sb);
        } finally {

            if (br != null) {
                br.close();
            }
        }
    }

    public static void stringToDom(String xmlSource) throws IOException {
        java.io.FileWriter fw = new java.io.FileWriter("URLxmlexample.xml");
        fw.write(xmlSource);
        fw.close();
    }
}