public class GopherRow {
    public GopherItemType itemType;

    public String userDisplayString;

    public String selector;

    public String hostname;

    public String port;

    public GopherRow(GopherItemType itemType, String userDisplayString, String selector, String hostname, String port) {
        this.itemType = itemType;
        this.userDisplayString = userDisplayString;
        this.selector = selector;
        this.hostname = hostname;
        this.port = port;
    }
}
