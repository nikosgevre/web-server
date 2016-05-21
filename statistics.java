import java.util.*;
import java.lang.*;
import gr.uth.inf.ce325.xml_parser.*;
import java.net.*;
import java.io.*;
import java.text.*;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class statistics implements Runnable {
	Socket cl;
	
	public statistics(Socket cl) {
		this.cl = cl;
	}
	public void run() {
		printStats();
	}
	
	public synchronized void printStats() { 
		String header = "", input = "";
		int i = 0;
		double average = 0;
		if(conn.requests != 0) {
			System.out.println(client.duration);
			System.out.println(conn.requests);
			average = client.duration/conn.requests;
			System.out.println(average);
		}
		long durat = System.currentTimeMillis()/1000 - tester.secs;
		int day = (int) TimeUnit.SECONDS.toDays(durat);
		long hours = TimeUnit.SECONDS.toHours(durat) -
                 TimeUnit.DAYS.toHours(day);
		long minute = TimeUnit.SECONDS.toMinutes(durat) - 
                  TimeUnit.DAYS.toMinutes(day) -
                  TimeUnit.HOURS.toMinutes(hours);
		long second = TimeUnit.SECONDS.toSeconds(durat) -
                  TimeUnit.DAYS.toSeconds(day) -
                  TimeUnit.HOURS.toSeconds(hours) - 
                  TimeUnit.MINUTES.toSeconds(minute);
		String running = day + " days, " + hours + " hours, " + minute + " min, " + second + " sec";
		String output = "<!DOCTYPE html><html><head><style> .size, .date {padding: 0 30px} h1.header {color: red; vertical-align: middle;}</style><title>CE325 HTTP Server Statistics</title>		<h1 class=\"header\">CE325 HTTP Server Statistics</h1></head><body><table><tr><th>Category</th><th>Statistics</th><tr><tr><td>Started At:</td><td>"+tester.time+"</td></tr><tr><td>Running for: </td><td>"+running+"</td></tr><tr><td>All Serviced Requests</td><td>:</td><td>"+conn.requests+"</td></tr><tr><td>HTTP 400 Requests</td><td>:</td><td>"+client.errors_400+"</tr><tr><td>HTTP 404 Requests</td><td>:</td><td>"+client.errors_404+"</tr><tr><td>HTTP 405 Requests</td><td>:</td><td>"+client.errors_405+"</tr><tr><td>HTTP 500 Requests</td><td>:</td><td>"+client.errors_500+"</tr><tr><td>Average Service Time (in msec)</td><td>:</td><td>"+average+"</td></tr></table></body></html></tr>";
		
		
		try(PrintWriter out = new PrintWriter(cl.getOutputStream(), true);
			BufferedReader in = new BufferedReader( new InputStreamReader(cl.getInputStream()));
		) {
			while(  i < 7 && (header = in.readLine())!=null) {
				if(header.contains("GET")) {
					input = header;
				}
				i++;
			}
			
			if(input.equals("")) {
				return;
			}
			if( input.contains("favicon.ico")) {
				return;
			}
			if(input.contains("GET") ) {
				if(input.contains("HTTP/1.1") || input.contains("HTTP/1.0")) {
					out.print("HTTP/1.1 200 OK\r\n");
					DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
					Date date = new Date();
					out.print("Date: " + dateFormat.format(date) + "\r\n");
					out.print("Server: CE325 (Java based server)\r\n");
					SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
					out.print("Content-Length: " + output.length());
					out.print("Connection: close" + "\r\n");
					out.print("Content-Type: text/html\r\n\r\n");
					out.flush();
					out.format(output);
					out.flush();
				}
			}
			out.close();
		} catch (IOException e) {
		
			System.out.print("500 Internal Server Error");
		}
	}

}