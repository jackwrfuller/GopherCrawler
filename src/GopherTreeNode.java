import java.util.ArrayList;
import java.util.List;

public class GopherTreeNode<T extends GopherTreeNodeType> {
    public T data;
    public List<GopherTreeNode<T>> children;
    public GopherTreeNode<T> parent;

    public GopherTreeNode(T data) {
        this.data = data;
        children = new ArrayList<>();
    }

    public void addChild(GopherTreeNode<T> node) {
        children.add(node);
    }

    public void addChild(T child) {
        GopherTreeNode<T> childNode = new GopherTreeNode<>(child);
        addChild(childNode);
    }


}
