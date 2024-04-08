import java.util.ArrayList;
import java.util.List;

public class GopherTreeNode {
    public GopherTreeNodeType data;
    public List<GopherTreeNode> children;
    public GopherTreeNode parent;

    public GopherTreeNode(GopherTreeNodeType data) {
        this.data = data;
        children = new ArrayList<>();
    }

    public void addChild(GopherTreeNode node) {
        children.add(node);
        node.parent = this;
    }

    public void addChild(GopherTreeNodeType child) {
        GopherTreeNode childNode = new GopherTreeNode(child);
        addChild(childNode);
    }


}
