package obs;
//
//import java.io.IOException;
//import java.net.Socket;
//import java.util.List;
//import java.util.Objects;
//import java.util.logging.Level;
//
public class GopherSpider {
//
//    private GopherClientInterface client;
//    private String host;
//    private int port;
//
//    private GopherTreeNode gopherServerTree;
//
//    public void buildServerTree(String host, int port, String selector) {
//        this.host = host;
//        this.port = port;
//
//        // TODO wrap GopherServerTree so it can store metadata as it is being built
//
//        gopherServerTree = new GopherTreeNode();
//        gopherServerTree.selector = selector;
//        crawl(gopherServerTree);
//
//        String fullServerURI = host + ":" + port + selector;
//        GopherClient.logger.log(Level.INFO, "Completed spider crawling of " + fullServerURI);
//    }
//
//    private void crawl(GopherTreeNode currentMenuNode) {
//        this.client = new GopherClient(host, port);
//        String response;
//        try {
//            response = client.send(currentMenuNode.selector);
//        } catch (IOException ignore) {
//            return;
//        }
//        currentMenuNode.data = GopherResponseProcessor.menuStringToGopherMenu(response);
//        List<GopherRow> rows = ((GopherMenu)currentMenuNode.data).getRows();
//
//        for (GopherRow row : rows) {
//            // Ignore informational rows
//            if (row.itemType == GopherItemType.INFO) {
//                continue;
//            }
//            // Check if row is an external server
//            if (!Objects.equals(row.hostname, this.host) || row.port != this.port) {
//                GopherExternalServer externalServer = new GopherExternalServer(row.itemType , row.hostname, row.port);
//                // Check if server is up
//                externalServer.isUp = isUp(externalServer);
//                // Add external server as leaf node
//                GopherTreeNode externalServerNode = new GopherTreeNode(externalServer);
//                externalServerNode.selector = row.selector;
//                currentMenuNode.addChild(externalServerNode);
//                continue;
//            }
//            // Check if row is a non-menu type
//            if (isNonMenuType(row)) {
//                GopherFile file = new GopherFile(row.itemType);
//                GopherTreeNode fileNode = new GopherTreeNode(file);
//                fileNode.selector = row.selector;
//                currentMenuNode.addChild(fileNode);
//                continue;
//            }
//            // If we are at this point, the row must be an internal menu,
//            // so we can now recurse.
//            GopherTreeNode menuNode = new GopherTreeNode();
//            menuNode.selector = row.selector;
//            currentMenuNode.addChild(menuNode);
//            crawl(menuNode);
//        }
//    }
//
//    private boolean isUp(GopherExternalServer server) {
//        try {
//            Socket clientSocket = new Socket(server.host, server.port);
//            return true;
//        } catch(IOException e) {
//            return false;
//        }
//    }
//
//    private boolean isNonMenuType(GopherRow row) {
//        return row.itemType != GopherItemType.MENU;
//    }
//
//    public static void main(String[] args) {
//        GopherSpider spider = new GopherSpider();
//
//        spider.buildServerTree("comp3310.ddns.net", 70, "/acme");
//
//        System.out.println(spider.gopherServerTree);
//    }

}
