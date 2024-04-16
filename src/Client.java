import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * A simple Gopher protocol client that sends String requests and receives
 * responses as byte arrays.
 * <p>
 * Based on and inspired by the TCPClient used in the tutorials, though
 * significantly different. For example, this client does not process responses
 * line-by-line, and nor does it return a String.
 */
public class Client {
    
    private final Socket sock = new Socket();
    // Host and port that client will contact
    private final String serviceHost;
    private final int servicePort;

    public Client(String host, int port) {
        serviceHost = host;
        servicePort = port;
    }

    /**
     * Sends a request to the Gopher server and returns the response.
     * <p>
     * Timeout is hardcoded to happen after 5 seconds and throws an IOException.
     * <p>
     * A response is limited to 100,000 bytes. If a server's response exceeds
     * this limit, an IOException is thrown.
     *
     * @param selector the resource or menu to receive.
     * @return the server response, if any.
     * @throws IOException if any networking errors occurred, such as timeout.
     */
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
        ByteArrayOutputStream response = new ByteArrayOutputStream();
        int nextChar;
        int byteCount = 0;
        while (true) {
            // Prevent 'firehose' behaviour from servers.
            if (byteCount > 100000) {
                throw new IOException("Maximum file size reached!");
            }
            nextChar = sock.getInputStream().read();
            if (nextChar < 0) {
                if (response.size() > 0)
                    break;
                else
                    return null;
            }
            response.write((byte)nextChar);
            byteCount++;
        }
        return response.toByteArray();
    }

}
