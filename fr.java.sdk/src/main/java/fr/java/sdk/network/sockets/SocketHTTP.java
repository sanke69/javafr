package fr.java.sdk.network.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

public class SocketHTTP extends SimpleSocketTCP {
	
	private URL target;

	public SocketHTTP(URL _httpServer) throws UnknownHostException, IOException {
		super(_httpServer);

	}
	public SocketHTTP(String _host, int _port) throws UnknownHostException, IOException {
		super(_host, _port);
	}

	public BufferedReader GET(String _request) throws IOException {
		return GET(_request, 0);
	}
	public BufferedReader GET(String _request, int _timeOut) throws IOException {
		socket.setSoTimeout(_timeOut);

		PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		writer.println("GET " + _request + " HTTP/1.1");
		writer.println();
		writer.flush();

		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	public byte[] GET_RAW(String _request, int _timeOut) throws IOException {
		socket.setSoTimeout(_timeOut);

		PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		writer.println("GET " + _request + " HTTP/1.1");
		writer.println();
		writer.flush();

		return null;
	}
	public BufferedReader POST(URL _httpServer) throws IOException {
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		writer.println("POST " + _httpServer.toString() + " HTTP/1.1");
		writer.println();
		writer.flush();

		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public static void main(String args[]) throws Exception {
		SocketHTTP http = new SocketHTTP("www.google.com", 80);


		BufferedReader br = http.GET("http://www.google.com/index.html");

		String line;
		while((line = br.readLine()) != null) {
			System.out.println(line);
			if(line.contains("</HTML>"))
				break;
		}
		
		br = http.GET("http://www.google.com/index.html", 20);
		try {
			while((line = br.readLine()) != null) {
				System.out.println(line);
				if(line.contains("</HTML>"))
					break;
			}
		} catch(SocketTimeoutException e) {
			System.out.println("Server has not respond during the timeout set");
		}
		
		http.close();

		System.out.println("DONE");
	}
}
