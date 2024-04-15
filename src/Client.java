import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    
    private Socket sock = new Socket();
    //  IP address and port that client will contact
    private String   serviceHost = "comp3310.ddns.net";
    private int      servicePort = 70;

    public Client() {}
    public Client(String host, int port) {
        serviceHost = host;
        servicePort = port;
    }

    public byte[] send(String selector) throws IOException {
        byte[] response;

        sock.connect(new InetSocketAddress(serviceHost, servicePort), 5000);
        sock.setSoTimeout(5000);
        sendRequest(selector + "\r\n");
    
        response = readReply();
        // Tell the server we are done
        try {
            sock.close();
        } catch (Exception ignored) {}

        return response;
    }

    private void sendRequest(String request) throws IOException {
        this.sock.getOutputStream().write(request.getBytes(StandardCharsets.UTF_8));
    }

    private byte[] readReply() throws IOException {
        ByteArrayOutputStream inData = new ByteArrayOutputStream();
        int     ch;
        int i = 0;
        while (true) {
            if (i > 100000) {
                throw new IOException("Maximum file size reached!");
            }
            ch = sock.getInputStream().read();
            if (ch < 0) {
                // Socket closed. If we have any data it is an incomplete
                // line, otherwise immediately return null
                if (inData.size() > 0)
                    break;
                else
                    return null;
            }
            inData.write((byte)ch);
            i++;
        }
        return inData.toByteArray();
    }

}
