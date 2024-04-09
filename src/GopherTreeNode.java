import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GopherTreeNode {
    public GopherTreeNodeType data;
    public List<GopherTreeNode> children;
    public GopherTreeNode parent;
    public String selector;

    public GopherTreeNode(GopherTreeNodeType data) {
        this.data = data;
        children = new ArrayList<>();
    }

    public GopherTreeNode() {}

    public void addChild(GopherTreeNode node) {
        children.add(node);
        node.parent = this;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        String name = data.type.name() + ", " + selector;
        buffer.append(prefix);
        buffer.append(name);
        buffer.append('\n');
        for (Iterator<GopherTreeNode> it = children.iterator(); it.hasNext();) {
            GopherTreeNode next = it.next();
            if (it.hasNext()) {
                next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }


}
