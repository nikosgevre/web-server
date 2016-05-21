import java.util.*;
import java.lang.*;
import gr.uth.inf.ce325.xml_parser.*;
import java.net.*;
import java.io.*;
import java.text.*;
import java.nio.file.Files;


public class conn implements Runnable {
	String loc;
	String location;
	static int requests;
	
	public conn(String loc, String location) {
		this.loc = loc;
		this.location = location;
	}
	
	public void run() {
		if(loc.contains("listen")) {
			listen(loc.substring(6,loc.length()));
		}
		else if(loc.contains("statistics")) {
			statistics(loc.substring(10,loc.length()));
		}
	}
	
	
	public synchronized void listen(String providerPort){
		int portNumber = Integer.parseInt(providerPort);
		String client;

		try (
			ServerSocket serverSocket = new ServerSocket(Integer.parseInt(providerPort));
		)
		{
			Socket clientSocket;
			while (true) {
				clientSocket = serverSocket.accept();
				requests++;
				Runnable cl = new client(clientSocket, location);
				new Thread(cl).start();
			}



		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
			+ portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		} 
		
	}
	
	
 	
	
	
	public synchronized void statistics(String statsPort){
		int portNumber = Integer.parseInt(statsPort);
		String client;
		try (
			ServerSocket serverSocket = new ServerSocket(Integer.parseInt(statsPort));
		)
		{
			Socket clientSocket;
			while (true) {
				clientSocket = serverSocket.accept();
				Runnable cl = new statistics(clientSocket);
				new Thread(cl).start();
			}

			

		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
			+ portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		} 
	}
	
	
}