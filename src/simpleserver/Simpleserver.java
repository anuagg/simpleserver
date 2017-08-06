package simpleserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author Ilmi
 */
public class Simpleserver {
protected Hashtable headers = new Hashtable();
protected String method = "";
protected String httpVersion = "";
protected String URI = "";
protected byte[] body = new byte[0];
protected boolean scriptRequest = false;
protected String pathInfo = "";
protected String queryString = "";
public static void main(String[] args) {
// TODO Auto-generated method stub
    ServerSocket ding = null;
    Socket dong = null;
    String resource = null;

    try {
        ding = new ServerSocket(1299);
        System.out.println("Opened socket " + 1299);
        while (true) {
        // keeps listening for new clients, one at a time
            try {
            dong = ding.accept(); // waits for client here
            }
            catch (IOException e) {
                System.out.println("Error opening socket");
                System.exit(1);
            }
            InputStream stream = dong.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            try {
                        // read the first line to get the request method, URI and HTTP version
                        String line = in.readLine();
                        StringTokenizer st = new StringTokenizer(line);
                        String method = st.nextToken();
                        String tmp = st.nextToken();
                        if (tmp.length() > 1)
                            resource = tmp.substring(1);
                        System.out.println("Requested Resource is " + resource);

                        // read headers
                        line = in.readLine();
                        while (line != null && line.trim().length() > 0) {

                                int index = line.indexOf(": ");
                                if (index > 0) {
                                    //headers.put(line.substring(0, index), line.substring(index + 2));
                                    System.out.println(line);
                                } else {
                                    break;
                                }
                                    line = in.readLine();
                                }
                 }
            catch (IOException e) {
                System.out.println("Error reading body");
                System.exit(1);
            }
            BufferedOutputStream out = new BufferedOutputStream(dong.getOutputStream());
            PrintWriter writer = new PrintWriter(out,true);  // char output to the client
            
            
            Response r = ResponseFactory.getResponse(resource);
            
                  if(r.body != null)
                  {
                      out.write(r.response.getBytes());
                      out.write(r.body);
                      out.flush();
                  }
                  else
                  {
                       BufferedWriter outwriter = new BufferedWriter( new OutputStreamWriter(out, "UTF-8"));
                       outwriter.write(r.response);
                       outwriter.flush();
                  }
            resource = null;
            dong.close();
        }
       

    }
        catch (IOException e) {
        System.out.println("Error opening socket");
        System.exit(1);
        }
}

}