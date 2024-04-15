import java.text.SimpleDateFormat;
import java.util.*;

public class Crawler {

    private GopherClient client;
    private final String host;
    private final int port;
    private TreeNode serverTree;

    private final Set<String> visited = new HashSet<>();
    private final Set<String> externalSites = new HashSet<>();

    private int numDirectories = 0;
    private final Set<String> textFiles = new HashSet<>();
    private final Set<String> binaryFiles = new HashSet<>();

    private final Set<String> invalidLinks = new HashSet<>();

    private int smallestBinaryFileSize = Integer.MAX_VALUE;
    private int largestBinaryFileSize = Integer.MIN_VALUE;

    private String smallestTextFile;

    private int largestTextFileSize = Integer.MIN_VALUE;


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
        String timestamp = new SimpleDateFormat("HH.mm.ss").format(new Date());
        System.out.println( "[" + timestamp + "]: " + parent.selector);
        if (parent.type == GopherItemType.ERROR) {
            invalidLinks.add(parent.selector);

            if (!Objects.equals(parent.host, this.host) || parent.port != this.port) {
                parent.status = Status.FOREIGN;
                externalSites.add(parent.host + ":" + port);
                return;
            }
            return;

        }
        if (!Objects.equals(parent.host, this.host) || parent.port != this.port) {
            parent.status = Status.FOREIGN;
            externalSites.add(parent.host + ":" + port);
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
            textFiles.add(parent.selector);
            if (parent.size > largestTextFileSize) {
                largestTextFileSize = parent.size;
            }
            if (smallestTextFile == null || parent.size < smallestTextFile.length()) {
                smallestTextFile = response;
            }
            return;
        }
        if (parent.type == GopherItemType.BINARY) {
            setBinaryFileSize(parent, response);
            binaryFiles.add(parent.selector);
            if (parent.size > largestBinaryFileSize) {
                largestBinaryFileSize = parent.size;
            }
            if (parent.size < smallestBinaryFileSize) {
                smallestBinaryFileSize = parent.size;
            }

            return;
        }
        if (parent.type != GopherItemType.MENU) {
            // parent node is of 'other' type which we do not care about
            parent.size = -1;
            return;
        }
        // Parent node must be a menu
        numDirectories++;
        addChildren(parent, response);
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

    public static void report(Crawler crawler) {
        System.out.println("===================STATISTICS===================");
        System.out.printf("Number of directories: %s\n", crawler.numDirectories);
        System.out.printf("Number of text files: %s\n", crawler.textFiles.size());
        System.out.printf("Number of binary files: %s\n", crawler.binaryFiles.size());
        System.out.printf("Largest text file size: %s\n", crawler.textFiles.isEmpty() ? "N/A" : crawler.largestTextFileSize);
        System.out.printf("Largest binary file size: %s\n", crawler.binaryFiles.isEmpty() ? "N/A" : crawler.largestBinaryFileSize);
        System.out.printf("Smallest binary file size: %s\n", crawler.binaryFiles.isEmpty() ? "N/A" : crawler.smallestBinaryFileSize);
        System.out.printf("Number of invalid references: %s\n", crawler.invalidLinks.size());
        System.out.printf("Number of external references: %s\n", crawler.externalSites.size());

        System.out.println("Text files found were: ");
        var sortedTextFileList = new ArrayList<>(crawler.textFiles);
        Collections.sort(sortedTextFileList);
        for (String file : sortedTextFileList) {
            System.out.println(" - " + file);
        }

        System.out.println("Binary files found were: ");
        var sortedBinaryFileList = new ArrayList<>(crawler.binaryFiles);
        Collections.sort(sortedBinaryFileList);
        for (String file : sortedBinaryFileList) {
            System.out.println(" - " + file);
        }
    }

    public static void main(String[] args) {
        Crawler crawler = new Crawler("comp3310.ddns.net", 70);
        crawler.buildServerTree("");
        System.out.println(crawler.serverTree);
        report(crawler);
    }


}
