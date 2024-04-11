import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.logging.*;

public class GopherClient {

    private Socket sock = new Socket();
    //  IP address and port that client will contact
    private String   serviceHost = "127.0.0.1";
    private int      servicePort = 70;

    static final Logger logger = Logger.getLogger(GopherClient.class.getName());
    public GopherClient() {}
    public GopherClient(String host, int port) {
        setTarget(host, port);
    }
    public void setTarget(String host, int port) {
        serviceHost = host;
        servicePort = port;
    }

//    public String sendWithTimeLimit(String request, int timelimit) throws ExecutionException, InterruptedException, TimeoutException {
//        ExecutorService executor = Executors.newCachedThreadPool();
//        Callable<String> task = new Callable<String>() {
//            public String call() throws IOException {
//                return send(request);
//            }
//        };
//        Future<String> future = executor.submit(task);
//        try {
//            return future.get(timelimit, TimeUnit.SECONDS);
//        } catch (TimeoutException ex) {
//            // handle the timeout
//        } catch (InterruptedException e) {
//            // handle the interrupts
//        } catch (ExecutionException e) {
//            // handle other exceptions
//        } finally {
//            future.cancel(true);
//        }
//        return "";
//    }


    /**
     * Send request to server and wait for response.
     * @param request the query to be made to the Gopher server.
     * @return the response string.
     */
    public String send(String request) throws IOException {

        String response = "l o n g   b o i\n\n.\n";

        sock.connect(new InetSocketAddress(serviceHost, servicePort), 5000);
        sock.setSoTimeout(5000);
        logger.log(Level.INFO, "Connection opened.");
        sendRequest(sock, request);
        response = readReply(sock);
        logger.log(Level.INFO, "Connection closed.");
        //System.out.println("Client close");
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
        int i = 0;
        while (i < 20) {
            String line = SockLine.readLine(sock);
            if (line == null || line.isEmpty()) {
                break;
            }
            System.out.println(line);
            reply.append(line);
            i++;
        }

        logger.log(Level.INFO, "Server response received");
        System.out.println(reply);
        return reply.toString();
    }




}

