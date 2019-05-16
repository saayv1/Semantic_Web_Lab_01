package jena.examples.rdf ;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.*;

public class Lab1p4 extends Object {
	public static void main (String args[]) throws IOException {
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);

        // some definitions
        String personURI    = "http://utdallas/semclass";
        String honorificPrefix = "Dr";
        String givenName    = "Viera";
        String familyName   = "Chandler";
        String title		= "South West Division President";
        String fullName     = honorificPrefix + ". " + givenName + " " + familyName;
        String company		= "Allied Semantics, U.S.";
        String bday			= "January 15, 1964";
        String email		= "vechandler@alliedsem.com";
        String resourceID	= "/534772";

        String directory = "MyDatabases/Dataset1" ;
        Dataset dataset = TDBFactory.createDataset(directory) ;
    	dataset.begin(ReadWrite.WRITE);
        Model modelTDB = dataset.getDefaultModel();
        modelTDB.createResource(personURI+resourceID)
        .addProperty(VCARD.FN, fullName)
        .addProperty(VCARD.N, 
                     modelTDB.createResource()
                          .addProperty(VCARD.Given, givenName)
                          .addProperty(VCARD.Family, familyName)
                          .addProperty(VCARD.Prefix, honorificPrefix))
        .addProperty(VCARD.TITLE, title )
        .addProperty(VCARD.BDAY,bday)
        .addProperty(VCARD.EMAIL, email)
        .addProperty(VCARD.ORG, company);
        modelTDB.setNsPrefix(personURI, personURI+resourceID);
        PrintWriter xmltype = new PrintWriter( "Lab1p2_vns170230.xml" );
        PrintWriter ntptype = new PrintWriter( "Lab1p2_vns170230.ntp" );
        PrintWriter n3type = new PrintWriter( "Lab1p2_vns170230.n3" );

        
        modelTDB.write(xmltype, "RDF/XML");
        modelTDB.write(ntptype, "N-TRIPLE");
        modelTDB.write(n3type, "N3");
        dataset.commit();
        dataset.end(); 

    	dataset.begin(ReadWrite.WRITE);        
        Model modelo = dataset.getNamedModel("myrdf"); 
        InputStream inFile = FileManager.get().open("vns170230_FOAFFriends.rdf");
        modelo.read(inFile, "myrdf");
        PrintWriter xmltype4 = new PrintWriter( "Lab1p4_vns170230.xml" );
        PrintWriter ntptype4= new PrintWriter( "Lab1p4_vns170230.ntp" );
        PrintWriter n3type4 = new PrintWriter( "Lab1p4_vns170230.n3" );
        modelo.write(xmltype4, "RDF/XML");
        modelo.write(ntptype4, "N-TRIPLE");
        modelo.write(n3type4, "N3");  
   
        dataset.commit();
        dataset.end();
        dataset.close();
        
    }
}