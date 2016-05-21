import gr.uth.inf.ce325.xml_parser.*;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.nio.file.Files;

public class parser{
	public Node getNode(String nodeName, Node nd){
		Node nm;
		int i = 0;
		int len = nd.getChildren().size();
		
		if(!nd.getName().equals(nodeName)) {
			if(len != 0) {
				while(len > i) {
					nm = getNode(nodeName,nd.getChild(i));
					if(nm != null) {
						return(nm);
					}
					i++;
				}
			}
		}
		else {
			return(nd);
		}
		
		return(null);
		
	}
	
	public void createFile(Node nd) {
		Node nm;
		nm = getNode("access",nd);
		File file = new File(nm.getFirstAttribute().getValue());
        FileWriter outputStream = null;
 
        try {
            outputStream = new FileWriter("."+nm.getFirstAttribute().getValue(), true);
			outputStream.close();
           
        } catch (IOException e) {
			
			
			System.out.print(e);
		}
		
		nm = getNode("error",nd);
		
		try {
            outputStream = new FileWriter("."+nm.getFirstAttribute().getValue(),true);
			outputStream.close();
           
        } catch (IOException e) {
			
			
			System.out.print(e);
		}
	}
}