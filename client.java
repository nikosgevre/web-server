import java.util.*;
import java.lang.*;
import gr.uth.inf.ce325.xml_parser.*;
import java.net.*;
import java.io.*;
import java.text.*;
import java.nio.file.Files;



public class client implements Runnable {
	Socket cl;
	String location;
	String[] mime;
	String[] types;
	FileWriter outputStream, outErrorStream;
	String logOutput = "";
	String erOutput = "";
	Node nm, ne;
	int flag;
	static int errors_400 = 0, errors_404 = 0, errors_405 = 0, errors_500 = 0;
	static double duration = 0;
	
	public client(Socket cl, String location) {
		this.cl = cl;
		this.location = location;
		mime = new String[15];
		types = new String[15];
		outputStream = null;
		outErrorStream = null;
		logOutput = "";
		erOutput = "";
		nm = ne = null;
		flag = 0;
	}
	
	public void run() {
		setMime();
		response();
		try {
			cl.close();
		} catch (IOException e) {
			
			System.out.println(e);
		}
		try{
			outputStream = new FileWriter(("."+nm.getFirstAttribute().getValue()), true);
			outErrorStream = new FileWriter(("."+ne.getFirstAttribute().getValue()), true);
			if(logOutput != "") {
				outputStream.write(logOutput);
				outputStream.flush();
			}else {
				outErrorStream.write(erOutput);
				outErrorStream.flush();
			}
			outputStream.close();
			outErrorStream.close();
		}catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void setMime() {
		mime[0] = "text/plain";
		types[0] = ".txt";
		
		mime[1] = "image/jpeg";
		types[1] = ".jpg";
		
		mime[2] = "image/png";
		types[2] = ".png";
		
		mime[3] = "image/tiff";
		types[3] = ".tiff";
		
		mime[4] = "image/bmp";
		types[4] = ".bmp";
		
		mime[5] = "image/gif";
		types[5] = ".gif";
		
		mime[6] = "video/x-msvideo";
		types[6] = ".avi";
		
		mime[7] = "video/mp4";
		types[7] = ".mp4";
		
		mime[8] = "audio/mpeg";
		types[8] = ".mp3";
		
		mime[9] = "audio/ogg";
		types[9] = ".ogg";
		
		mime[10] = "application/pdf";
		types[10] = ".pdf";
		
		mime[11] = "application/vnd.ms-excel";
		types[11] = ".xls";
		
		mime[12] = "application/msword";
		types[12] = ".doc";
		
		mime[13] = "application/vnd.ms-powerpoint";
		types[13] = ".pps";
		
		mime[14] = "application/vnd.ms-powerpoint";
		types[14] = ".ppt";
				
	}
	
	
	public synchronized String htmlMaker (String loc) {

		
		File folder = new File(loc);
		
		File[] arxeia;
		arxeia=folder.listFiles();
		int len = arxeia.length;
		String arxeio;
		String newLoc;
		parser path = new parser();
		DocumentBuilder docBuilder = new DocumentBuilder( );
		Document doc = docBuilder.getDocument(location);   
		Node nd;
		nd = path.getNode("document-root",doc.getRootNode());
		int megethos;
		String index = loc.replace("path/to/document-root","");
		String arxi="";
		if(flag==0){
			arxi="<html><head><style> .size, .date {padding: 0 30px} h1.header {color: red; vertical-align: middle;}</style><title>CE325 HTTP Server</title><h1 class=\"header\"><img src=\""+"/icons/java.png"+"\"/ > CE325 HTTP Server</h1><h1>Current Folder: / "+ index +"</h1></head><body><table><tr><th></th><th>Filename</th><th>Size</th><th>Last Modified</th><tr>";

		}else{
			arxi="<html><head><style> .size, .date {padding: 0 30px} h1.header {color: red; vertical-align: middle;}</style><title>CE325 HTTP Server</title><h1 class=\"header\"><img src=\""+"/icons/java.png"+"\"/ > CE325 HTTP Server</h1><h1>Current Folder:  "+ index +"</h1></head><body><table><tr><th></th><th>Filename</th><th>Size</th><th>Last Modified</th><tr><tr><td><img src=\""+"/icons/back.gif"+"\"/ ></td><td class=\"link\"><a href=\"..\" >Parent Directory</a></td><td class=\"size\"></td><td class=\"date\"></td></tr>";
		}
		
		String telos="<tr><th colspan=\"5\"><hr></th></tr></table> </body></html>";
		
		String rootLoc=nd.getFirstAttribute().getValue().substring(1,nd.getFirstAttribute().getValue().length()-1);
		String mesi = "";
		SimpleDateFormat sdf;
		String imerominia="";
		double size=0;
		int j=0;
		String sizeFormat = "";
		String eikona="/icons/unknown.gif";
		while (j<len){
			
			sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
			megethos=0;
			size=0;
			imerominia="";
			arxeio = arxeia[j].getName().replace(loc, "");
			newLoc=arxeia[j].toString().replace(rootLoc,"");
			megethos=newLoc.length();
			newLoc=newLoc.substring(0,megethos);
			arxeio = arxeia[j].getName().replace(loc, "");
			if(arxeia[j].isDirectory()){
				imerominia=sdf.format(arxeia[j].lastModified());
				size=arxeia[j].length();
				sizeFormat = size + " B";
				if(size >= 1024) {
					size = size/1024;
					sizeFormat = size + " KB";
					if(size >= 1024) {
						size = size/1024;
						sizeFormat = size + " MB";
						if(size >= 1024) {
							size = size/1024;
							sizeFormat = size + " GB";
						}
					}
				}
				eikona="/icons/folder.gif";
			}else{
				imerominia=sdf.format(arxeia[j].lastModified());
				size=arxeia[j].length();
				sizeFormat = size + " B";
				if(size >= 1024) {
					size = size/1024;
					sizeFormat = size + " KB";
					if(size >= 1024) {
						size = size/1024;
						sizeFormat = size + " MB";
						if(size >= 1024) {
							size = size/1024;
							sizeFormat = size + " GB";
						}
					}
				}

				if(arxeia[j].getName().contains(types[1]) || arxeia[j].getName().contains(types[2]) || arxeia[j].getName().contains(types[3]) || arxeia[j].getName().contains(types[4]) ){
					eikona="/icons/image2.gif";
				}else if(arxeia[j].getName().contains(types[8]) || arxeia[j].getName().contains(types[9])){
					eikona="icons/sound2.gif";
				}else if(arxeia[j].getName().contains(types[0])){
					eikona="/icons/text.gif";
				}else if(arxeia[j].getName().contains(types[6]) || arxeia[j].getName().contains(types[7]) ){
					eikona="/icons/movie.gif";
				}else{
					eikona="/icons/unknown.gif";
				}
				
			}
			
			mesi+="<tr><td><img src=\""+eikona+"\"/ ></td><td class=\"link\"><a href=\"" + newLoc + "\">" + arxeio + "/</a></td><td class=\"size\">"+sizeFormat+"</td><td class=\"date\">"+imerominia+"</td></tr>";

			
			j++;
			eikona="/icons/unknown.gif";
		}
		
		String outHTML = arxi  + mesi + telos;
		return(outHTML);
	}
	
	
	
	
	public synchronized void response() {  

		parser path = new parser();
		DocumentBuilder docBuilder = new DocumentBuilder( );
		Document doc = docBuilder.getDocument(location);   
		Node nd;
		nd = path.getNode("document-root",doc.getRootNode());
		nm = path.getNode("access", doc.getRootNode());
		ne = path.getNode("error", doc.getRootNode());
		
		int i = 0;
		File folder = new File(nd.getFirstAttribute().getValue().substring(1,nd.getFirstAttribute().getValue().length()-1));
		File[] arxeia;
		arxeia=folder.listFiles();
		int len = arxeia.length;
		String output, loc;
		int file = 0;
		String total_path = "";
		String usr_agent ="";
		
		double start = 0;
		double end = 0;
		
		
		
		try(PrintWriter out = new PrintWriter(cl.getOutputStream(), true);
			BufferedReader in = new BufferedReader( new InputStreamReader(cl.getInputStream()));
		) {
			
			erOutput += logOutput += cl.getRemoteSocketAddress().toString() + " - ";
			DateFormat datesFormat = new SimpleDateFormat("[EEE MMM d HH:mm:ss z yyyy]");
			Date dates = new Date();
			erOutput = logOutput += datesFormat.format(dates) + " ";
			
			String input = "", header = "";
			
			while(  i < 7 && (header = in.readLine())!=null) {
				if(header.contains("User-Agent:")) {
					usr_agent = header;
				}
				if(header.contains("GET")) {
					start = System.currentTimeMillis();
					input = header;
				}
				i++;
			}
			erOutput = logOutput += input + " -> ";
			
			if(input.equals("")) {
				return;
			}
			if(input.substring(4, input.length()-9).contains(" ")) {
				errors_400++;
				erOutput += "400 Bad Request";
				end = System.currentTimeMillis();
				out.println("400 Bad Request");
				return;
			}
			if(input.contains("%20")) {
				input = input.replace("%20", " ");
			}
			if( input.contains("favicon.ico")) {
				end = System.currentTimeMillis();
				return;
			}
			if(input.contains("GET") ) {
				if(input.contains("HTTP/1.1") || input.contains("HTTP/1.0")) {
					input = input.substring(4, input.length()-9);
					File existing = new File(nd.getFirstAttribute().getValue().substring(1,nd.getFirstAttribute().getValue().length()-1) + input);
					if(!existing.exists()) {
						errors_404++;
						logOutput = "";
						end = System.currentTimeMillis();
						erOutput += "404 Not Found";
						out.println("404 Not Found");
						return;
					}
					out.print("HTTP/1.1 200 OK\r\n");
					logOutput += "200 OK ";
					logOutput += usr_agent;
					DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
					Date date = new Date();
					out.print("Date: " + dateFormat.format(date) + "\r\n");
					out.print("Server: CE325 (Java based server)\r\n");
					if(input.equals("/")) {
						flag = 0;
						SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
						out.print("Last-Modified: " +sdf.format(folder.lastModified()) + "\r\n");
						out.print("Content-Length: " +  htmlMaker(nd.getFirstAttribute().getValue().substring(1,nd.getFirstAttribute().getValue().length()-1)).length() + "\r\n");
						out.print("Connection: close" + "\r\n");
						out.print("Content-Type: text/html\r\n\r\n");
						out.flush();
						total_path = nd.getFirstAttribute().getValue().substring(1,nd.getFirstAttribute().getValue().length()-1);
						output = htmlMaker(total_path);
						out.format(output);
						end = System.currentTimeMillis();
						
					}
					else {
						flag = 1;
						if(input!=null){
							SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
							for(i=0;i<types.length;i++) {
								if(input.contains(types[i])) {
									file = 1;
									break;
								}
							}

							if(input.length()>0 && total_path!=null){
								input = nd.getFirstAttribute().getValue().substring(1,nd.getFirstAttribute().getValue().length()-1) + input;
							}
							out.flush();
							File arch = new File(input);
							if(file==0) {
								output = htmlMaker(input);
								out.print("Last-Modified: " +sdf.format(arch.lastModified()) + "\r\n");
								out.print("Content- Length: " + output.length() + "\r\n");
								out.print("Connection: close" + "\r\n");
								out.print("Content-Type: text/html\r\n\r\n");
								out.flush();
								out.format(output);
								end = System.currentTimeMillis();
							}
							else {
								out.print("Last-Modified: " +sdf.format(arch.lastModified()) + "\r\n");
								out.print("Content-Length: " + arch.length() + "\r\n");
								out.print("Connection: close" + "\r\n");
								out.print("Content-Type: " + mime[i] + "\r\n\r\n");
								out.flush();
								byte[] buffer = Files.readAllBytes(arch.toPath());
								OutputStream stream = new BufferedOutputStream(cl.getOutputStream());
								stream.write(buffer);
								stream.flush();
								end = System.currentTimeMillis();
								stream.close();

							}
						}
					}
				}
				else {
					errors_400++;
					end = System.currentTimeMillis();
					erOutput += "400 Bad Request";
					out.println("400 Bad Request");
					return;
				}
				logOutput += "\n";
				
			}
					
			else {
				errors_405++;
				logOutput = "";
				end = System.currentTimeMillis();
				erOutput += "405 Method Not Allowed";
				out.println("405 Method Not Allowed");
			}
			out.close();
			
		} catch (IOException e) {
			errors_500++;
			end = System.currentTimeMillis();
			logOutput = "";
			erOutput += "500 Internal Server Error " + "Exception’s Stack Trace = " + e.getStackTrace();
			System.out.print("500 Internal Server Error ");
		}
		erOutput += "\n";
		
		duration = duration + end-start;
		
		return;
	}
	
} 
