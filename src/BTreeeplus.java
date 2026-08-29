import java.util.ArrayList;

public class BTreeeplus {
    NodeTree root;
    int max_ids = 3;

    public void Add(int id, long offset) {

        if (root == null) {
            root = new NodeTree(true);
            root.ids.add(id);
            root.offsets.add(offset);
            return;
        }

        NodeTree current = root;
        while (!current.isLeaf) {
            int i = 0;
            while(i<current.ids.size() && id>=current.ids.get(i)){
                i++;
            }
            current = current.children.get(i);
        }
        int j = 0;
        while( j<current.ids.size() && id>current.ids.get(j)){
            j++;
        }
        current.ids.add(j, id);
        current.offsets.add(j, offset);

        if(current.ids.size() > max_ids){
            SplitLeaf(current);
        }
    }

    public void SplitLeaf(NodeTree node) {

        NodeTree right = new NodeTree(true);
        int mid = node.ids.size() / 2;

        while(mid<node.ids.size()){
            right.ids.add(node.ids.get(mid));
            right.offsets.add(node.offsets.get(mid));
            node.ids.remove(mid);
            node.offsets.remove(mid);
        }

        right.next = node.next;
        node.next = right;

        if(node.parent==null){
            NodeTree newRoot = new NodeTree(false);
            newRoot.ids.add(right.ids.get(0));
            newRoot.children.add(node);
            newRoot.children.add(right);
            node.parent = newRoot;
            right.parent = newRoot;

            root = newRoot;

        }
        else{
            right.parent = node.parent;
            NodeTree parent = node.parent;
            int i = 0;
            while(i<parent.ids.size() && right.ids.get(0) > parent.ids.get(i)){
                i++;
            }
            parent.ids.add(i, right.ids.get(0));
            parent.children.add(i+1, right);

            if(parent.ids.size() > max_ids){
                SplitInternalLeaf(parent);
            }
        }

    }

    public void SplitInternalLeaf(NodeTree node) {

        int mid = node.ids.size() / 2;

        int promoted = node.ids.get(mid);

        NodeTree right = new NodeTree(false);

        while (node.ids.size() > mid + 1) {
            right.ids.add(node.ids.remove(mid + 1));
        }

        node.ids.remove(mid);

        while (node.children.size() > mid + 1) {
            NodeTree child = node.children.remove(mid + 1);

            right.children.add(child);
            child.parent = right;
        }

        if (node.parent == null) {

            NodeTree newRoot = new NodeTree(false);

            newRoot.ids.add(promoted);

            newRoot.children.add(node);
            newRoot.children.add(right);

            node.parent = newRoot;
            right.parent = newRoot;

            root = newRoot;
        }

        else {

            NodeTree parent = node.parent;

            int childIndex = parent.children.indexOf(node);

            parent.ids.add(childIndex, promoted);
            parent.children.add(childIndex + 1, right);

            right.parent = parent;

            if (parent.ids.size() > max_ids) {
                SplitInternalLeaf(parent);
            }
        }
    }

    public ArrayList<Long> FindBetween(int min, int max) {

        ArrayList<Long> results = new ArrayList<>();

        if (root == null || min > max) {
            return results;
        }

        NodeTree current = root;

        while (!current.isLeaf) {

            int j = 0;

            while (j < current.ids.size() && min >= current.ids.get(j)) {
                j++;
            }

            current = current.children.get(j);
        }

        int i = 0;

        while (i < current.ids.size() && current.ids.get(i) < min) {
            i++;
        }

        while (current != null) {

            while (i < current.ids.size()) {

                int id = current.ids.get(i);

                if (id > max) {
                    return results;
                }

                results.add(current.offsets.get(i));
                i++;
            }

            current = current.next;
            i = 0;
        }

        return results;
    }
    public void printTree(){
        NodeTree current = root;
        while(!current.isLeaf){
            current = current.children.get(0);
        }
        while(current!=null){
            int i = 0;
            while(i<current.ids.size()){
                System.out.print(current.ids.get(i)+" ");
                i++;
            }
            current = current.next;
        }
    }

    public void printRoot() {

        System.out.println("Root ids: " + root.ids);

        for (int i = 0; i < root.children.size(); i++) {
            System.out.println(
                    "Child " + i + ": " + root.children.get(i).ids
            );
        }
    }

}
