import java.util.ArrayList;
public class NodeTree {
    boolean isLeaf;
    ArrayList<NodeTree> children;
    ArrayList<Integer> ids;
    ArrayList<Long> offsets;

    NodeTree parent;
    NodeTree next;

    public NodeTree(boolean isLeaf) {
        this.isLeaf = isLeaf;

        this.ids = new ArrayList<>();
        this.offsets = new ArrayList<>();
        this.children = new ArrayList<>();

        this.next = null;
        this.parent = null;
    }

}
