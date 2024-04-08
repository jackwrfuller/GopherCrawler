public interface GopherClientInterface {

    void setTarget(String serviceHost, int servicePort);

    String send(String request);
}
