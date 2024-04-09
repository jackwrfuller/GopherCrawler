public class GopherExternalServer extends GopherTreeNodeType{
    String host;
    int port;
    boolean isUp = false;
    public GopherExternalServer(String host, int port){
        this.host = host;
        this.port = port;
    }

}
