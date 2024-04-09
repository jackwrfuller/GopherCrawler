import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class GopherSpider {

    private GopherClientInterface client;
    private String host;
    private int port;

    private GopherTreeNode gopherServerTree;

    public void buildServerTree(String host, int port) {
        this.host = host;
        this.port = port;

        gopherServerTree = new GopherTreeNode();
        gopherServerTree.selector = "/";
        crawl(gopherServerTree);
    }

    private void crawl(GopherTreeNode currentMenuNode) {
        this.client = new GopherClient(host, port);
        String response;
        try {
            response = client.send(currentMenuNode.selector);
        } catch (IOException _) {
            return;
        }
        currentMenuNode.data = GopherResponseProcessor.menuStringToGopherMenu(response);
        List<GopherRow> rows = ((GopherMenu)currentMenuNode.data).getRows();

        for (GopherRow row : rows) {
            // Check if row is an external server
            if (!Objects.equals(row.hostname, this.host) || row.port != this.port) {
                GopherExternalServer externalServer = new GopherExternalServer(row.hostname, row.port);
                GopherTreeNode externalServerNode = new GopherTreeNode(externalServer);
                externalServerNode.selector = row.selector;
                currentMenuNode.addChild(externalServerNode);
                continue;
            }
            if (isNonMenuType(row)) {
                GopherFile file = new GopherFile(row.itemType);
                GopherTreeNode fileNode = new GopherTreeNode(file);
                fileNode.selector = row.selector;
                currentMenuNode.addChild(fileNode);
                continue;
            }
            // Row is an internal menu, so we can now recurse
            GopherTreeNode menuNode = new GopherTreeNode();
            menuNode.selector = row.selector;
            currentMenuNode.addChild(menuNode);
            crawl(menuNode);
        }
    }

    private boolean isNonMenuType(GopherRow row) {
        return row.itemType != GopherItemType.MENU;
    }















}
