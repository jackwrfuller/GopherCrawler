import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Crawler {

    private GopherClient client;
    private final String host;
    private final int port;
    private TreeNode serverTree;

    private final Set<String> visited = new HashSet<>();

    public Crawler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void buildServerTree(String selector) {
        serverTree = new TreeNode(GopherItemType.MENU, selector, host, port);
        crawl(serverTree);
    }

    public void addChildren(TreeNode parent, String response) {
        GopherMenu menu = GopherResponseProcessor.menuStringToGopherMenu(response);
        for (GopherRow row : menu.getRows()) {
            TreeNode child = new TreeNode(row.itemType, row.selector, row.hostname, row.port);
            parent.children.add(child);
        }
    }

    private void crawl(TreeNode parent) {
        if (!Objects.equals(parent.host, this.host) || parent.port != this.port) {
            parent.status = Status.FOREIGN;
            return;
        }
        if (visited.contains(parent.selector)) {
            return;
        }

        this.client = new GopherClient(host, port);
        String response = "";
        try {
            response = client.send(parent.selector);
            parent.status = Status.OK;
        } catch (Exception ignore) {
            // We abort early
           parent.status = Status.ERROR;
        }
        visited.add(parent.selector);

        if (parent.status == Status.ERROR) {
            parent.size = -1;
            return;
        }
        if (parent.type == GopherItemType.TEXT) {
            setTextFileSize(parent, response);
            return;
        }
        if (parent.type == GopherItemType.BINARY) {
            setBinaryFileSize(parent, response);
            return;
        }
        if (parent.type != GopherItemType.MENU) {
            // parent node is of 'other' type which we do not care about
            parent.size = -1;
            return;
        }
        addChildren(parent, response);
        // Parent must be a MENU type, assumed to already have children added
        for (TreeNode child : parent.children) {
            crawl(child);
        }
    }

    private void setTextFileSize(TreeNode parent, String response) {
        parent.size = response.length();
    }

    private void setBinaryFileSize(TreeNode parent, String response) {
        // TODO modify to handle binary file length differently
        parent.size = response.length();
    }

    public static void main(String[] args) {
        Crawler crawler = new Crawler("comp3310.ddns.net", 70);
        crawler.buildServerTree("/misc");
        System.out.println(crawler.serverTree);
    }


}
