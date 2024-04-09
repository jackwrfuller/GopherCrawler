import java.io.*;
import java.net.*;
import java.util.logging.*;

public class GopherClient implements GopherClientInterface{

    private Socket sock;
    //  IP address and port that client will contact
    private String   serviceHost = "127.0.0.1";
    private int      servicePort = 70;

    private static final Logger logger = Logger.getLogger(GopherClient.class.getName());
    public GopherClient() {}
    public GopherClient(String host, int port) {
        setTarget(host, port);
    }
    public void setTarget(String host, int port) {
        serviceHost = host;
        servicePort = port;
    }


    /**
     * Send request to server and wait for response.
     * @param request the query to be made to the Gopher server.
     * @return the response string.
     */
    public String send(String request) throws IOException {
        String response = "";
        sock = new Socket(serviceHost, servicePort);
        sendRequest(sock, request);
        response = readReply(sock);
        System.out.println("Client close");
        // Tell the server we are done
        try {
            SockLine.writeLine(sock, "BYE");
            sock.close();
        } catch (Exception ignored) {}

        return response;
    }

    /** Send our request to server */

    protected void sendRequest(Socket sock, String request)
            throws IOException
    {
        // No try: if anything goes wrong, higher level will handle
        SockLine.writeLine(sock, request);
        logger.log(Level.INFO, "Client request sent: " + request);
    }

    /** Read and print server response */

    protected String readReply(Socket sock)
            throws IOException
    {
        StringBuilder reply = new StringBuilder();
        while (true) {
            String line = SockLine.readLine(sock);
            if (line == null || line.isEmpty()) {
                break;
            }
            reply.append(line);
        }

        logger.log(Level.INFO, "Server response received");
        System.out.println(reply);
        return reply.toString();
    }




}

