import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class HashTable {

    Node[] buckets = new Node[10];
    int size;


    public long Find(int id)  {
        int idx = id % buckets.length;
        Node temp = buckets[idx];
        while(temp != null){
            if(temp.id==id){
                return temp.offset;
            }
            temp = temp.next;
        }
        return -1;
    }

    public void Add(int id, long offset){
        int idx = id % buckets.length;
        Node node = new Node(id, offset);
        Node temp = buckets[idx];
        buckets[idx] = node;
        node.next = temp;

        size++;
        double load = (double) size/buckets.length;

        if(load > 0.75){
            Resize();
        }


    }

    public void Resize(){
        Node[] old_buckets = buckets;
        buckets = new Node[old_buckets.length*2];

        size = 0;

        for(int i = 0; i < old_buckets.length; i++){
            Node temp = old_buckets[i];
            while(temp != null){
                Add(temp.id,temp.offset);
                temp = temp.next;
            }
        }

    }

    public int getSize(){
        return size;
    }

    public double getLoad(){
        return (double) size/buckets.length;
    }

    public int getBucket(){
        return buckets.length;
    }


}
