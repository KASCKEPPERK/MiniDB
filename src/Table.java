import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Table {
    ArrayList<Row> rows = new ArrayList<Row>();
    HashTable hashTable = new HashTable();
    RandomAccessFile file;

    public Table(String file) throws IOException {
        this.file = new RandomAccessFile(file, "rw");
        RebuildIndex();
    }

    public long Insert(Row row) throws IOException {
        long offset = file.length();

        file.seek(offset);

        file.writeInt(row.id);
        file.writeUTF(row.name);

        hashTable.Add(row.id, offset);

        return offset;
    }

    public Row getRow(int id) throws IOException {
        long offset = hashTable.Find(id);
        if(offset == -1) return null;
        file.seek(offset);

        int i = file.readInt();
        String name = file.readUTF();
        return new Row(i,name);
    }

    public Row getRowSimple(int id){
        for(Row row : rows){
            if(row.getId() == id){
                return row;
            }
        }
        return null;
    }

    public ArrayList<Row> getRows(ArrayList<Long> offsets) throws IOException {
        ArrayList<Row> results = new ArrayList<>();
        for(Long offset : offsets){
            if(offset == -1) continue;
            file.seek(offset);
            int i = file.readInt();
            String name = file.readUTF();
            results.add(new Row(i,name));
        }
        return results;
    }

    public ArrayList<Row> getRowsBetween(int min, int max) throws IOException {
        ArrayList<Row> results = new ArrayList<>();

        file.seek(0);
        while(file.getFilePointer() < file.length()){
            int id = file.readInt();
            String name = file.readUTF();
            Row row = new Row(id,name);
            if(id >= min && id <= max){
                results.add(row);
            }
        }
        return results;
    }

    public void saveToFile(String filename) throws IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(filename));

        for (Row row : rows) {
            out.writeInt(row.id);
            out.writeUTF(row.name);
        }

        out.close();
    }

    public void loadFromFile(String filename) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(filename));

        try {
            while (true) {
                int id = in.readInt();
                String name = in.readUTF();
                Row row = new Row(id, name);
                Insert(row);
            }
        }
        catch(EOFException e){
        }
        in.close();
        }

        public void RebuildIndex() throws IOException {
        file.seek(0);
            while(file.getFilePointer() < file.length()){
                long offset = file.getFilePointer();
                int id = file.readInt();
                String name = file.readUTF();
                hashTable.Add(id,offset);
            }
        }
    }

