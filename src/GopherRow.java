import java.util.Objects;

public class GopherRow {
    public GopherItemType itemType;

    public String userDisplayString;

    public String selector;

    public String hostname;

    public int port;

    public GopherRow(GopherItemType itemType, String userDisplayString, String selector, String hostname, int port) {
        this.itemType = itemType;
        this.userDisplayString = userDisplayString;
        this.selector = selector;
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GopherRow gopherRow = (GopherRow) o;
        return itemType == gopherRow.itemType && Objects.equals(userDisplayString, gopherRow.userDisplayString) && Objects.equals(selector, gopherRow.selector) && Objects.equals(hostname, gopherRow.hostname) && Objects.equals(port, gopherRow.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemType, userDisplayString, selector, hostname, port);
    }
}
