import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GopherSpider {

    private GopherClientInterface client;
    public GopherTreeNode crawl(String host, int port) {
        GopherTreeNode root;
        this.client = new GopherClient(host, port);
        String response;
        try {
            response = client.send("/");
        } catch (IOException e) {
            Logger.getLogger(GopherClient.class.getName()).log(Level.SEVERE, "Unable to connect");
            return null;
        }
        GopherMenu menu = GopherResponseProcessor.menuStringToGopherMenu(response);
        root = new GopherTreeNode(menu);
        crawlerHelper(root);

        return root;
    }

    private void crawlerHelper(GopherTreeNode currentMenuNode) {
        assert(currentMenuNode.data instanceof GopherMenu);

        var menuRows = ((GopherMenu) currentMenuNode.data).getRows();
        for (var row : menuRows) {
            if (row.itemType == GopherItemType.MENU) {
                String response;
                try {
                    response = client.send(row.selector);
                } catch (IOException e) {
                    Logger.getLogger(GopherClient.class.getName()).log(Level.SEVERE, "Unable to connect");
                    return;
                }
                GopherMenu menu = GopherResponseProcessor.menuStringToGopherMenu(response);
                var child = new GopherTreeNode(menu);
                currentMenuNode.addChild(child);

                crawlerHelper(child);
            }
            if (row.itemType == GopherItemType.TEXT) {
                String response;
                try {
                    response = client.send(row.selector);
                } catch (IOException e) {
                    Logger.getLogger(GopherClient.class.getName()).log(Level.SEVERE, "Unable to connect");
                    return;
                }
                GopherTextFile txtFile = new GopherTextFile(response);
                GopherTreeNode txtNode = new GopherTreeNode(txtFile);
                currentMenuNode.addChild(txtNode);
                return;
            }
            if (row.itemType == GopherItemType.BINARY) {
                String response;
                try {
                    response = client.send(row.selector);
                } catch (IOException e) {
                    Logger.getLogger(GopherClient.class.getName()).log(Level.SEVERE, "Unable to connect");
                    return;
                }
                GopherTextFile binFile = new GopherTextFile(response);
                GopherTreeNode binNode = new GopherTreeNode(binFile);
                currentMenuNode.addChild(binNode);
                return;
            }






        }


    }



    /**
     * Given a GopherMenu, return a list of menu selectors to be crawled.
     */
    public List<String> getMenuSelectorsFromMenu(GopherMenu menu) {
        List<String> selectors = new ArrayList<>();
        for (GopherRow row : menu.getRows()) {
            if (row.itemType == GopherItemType.MENU) {
                selectors.add(row.selector);
            }
        }
        return selectors;
    }



}
