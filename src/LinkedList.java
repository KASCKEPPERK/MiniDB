public class LinkedList {
    Node head;

    public LinkedList() {
        this.head = null;
    }

    public void add(Node node){
        Node temp = head;
        if(head == null){
            head = node;
            return;
        }
        while(temp.next != null){
            temp = temp.next;
        }
        temp.next = node;
    }

}
