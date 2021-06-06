package fr.java.sdk.network.sockets;
import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.*;

public class SocketSSL {

  public static void main(String args[]) throws Exception {

    String urlString = (args.length == 1) ? 
      args[0] : "http://www.verisign.com/index.html";
    URL url = new URL(urlString);

    // TODO: Check Java9 Functionality
//  Security.addProvider(
//    new com.sun.net.ssl.internal.ssl.Provider());

    SSLSocketFactory factory = 
      (SSLSocketFactory)SSLSocketFactory.getDefault();
    SSLSocket socket = 
      (SSLSocket)factory.createSocket(url.getHost(), 443);

    PrintWriter out = new PrintWriter(
        new OutputStreamWriter(
          socket.getOutputStream()));
    out.println("GET " + urlString + " HTTP/1.1");
    out.println();
    out.flush();

    BufferedReader in = new BufferedReader(
      new InputStreamReader(
      socket.getInputStream()));

    String line;

    while ((line = in.readLine()) != null) {
      System.out.println(line);
    }

    out.close();
    in.close();
  }
}