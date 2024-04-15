import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeNode {

    public GopherItemType type;

    public String selector;

    public String host;
    public int port;

    public int size;

    List<TreeNode> children = new ArrayList<>();

    public Status status = Status.OK;

    public TreeNode(GopherItemType type, String selector, String host, int port) {
        this.type = type;
        this.selector = selector;
        this.host = host;
        this.port = port;
    }


    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        String name = "[" + this.type.name() + ", \"" + this.selector + "\", " + this.status.name() + ", " + this.size + "]";
        buffer.append(prefix);
        buffer.append(name);
        buffer.append('\n');
        for (Iterator<TreeNode> it = children.iterator(); it.hasNext();) {
            TreeNode next = it.next();
            if (it.hasNext()) {
                next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }

}
