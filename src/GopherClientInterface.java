import java.io.IOException;

public interface GopherClientInterface {

    void setTarget(String serviceHost, int servicePort);

    String send(String request) throws IOException;
}
