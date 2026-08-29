public class Node {
    int id;
    long offset;
    Node next;

    public Node(int id, long offset) {
        this.id = id;
        this.offset = offset;
        this.next = null;
    }
}
