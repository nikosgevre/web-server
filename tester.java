import java.util.*;
import java.lang.*;
import gr.uth.inf.ce325.xml_parser.*;
import java.net.*;
import java.io.*;
import java.text.*;
import java.nio.file.Files;


public class tester {
	static String time = "";
	static long secs = 0;
	
	public static void main(String args[]) {
		DocumentBuilder docBuilder = new DocumentBuilder( );
		Document doc = docBuilder.getDocument( args[0] );
		
		Node nd;
		String text;
		int i = 0;
		String loc = "";
		
		
		parser pars = new parser();
		
		pars.createFile(doc.getRootNode());
		
		
		
		nd = pars.getNode("listen",doc.getRootNode());
		if(nd!=null) {
			if(nd.getAttributes().size()>0) {
				loc = "listen" + nd.getFirstAttribute().getValue();
				DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
				Date date = new Date();
				time = dateFormat.format(date);
				secs = System.currentTimeMillis()/1000;
				Runnable connect = new conn(loc, args[0]);
				new Thread(connect).start();
			}
		}
		else {
			System.out.println("Your XML file does not include the node lsiten.Please try again.");
		}
		
		
		nd =pars.getNode("statistics", doc.getRootNode());
		if(nd!=null) {
			if(nd.getAttributes().size()>0) {
				loc = "statistics" + nd.getFirstAttribute().getValue();
				Runnable stats = new conn(loc, args[0]);
				new Thread(stats).start();
			}
		}
		else {
			System.out.println("Your XML file does not include the node statistics.Please try again.");
		}
	}
}