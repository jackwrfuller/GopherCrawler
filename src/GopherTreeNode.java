import java.util.ArrayList;
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


}
