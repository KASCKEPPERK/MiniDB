import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        int total = 1_000_000;
        int min = 500_000;
        int max = 500_010;

        Table table = new Table("database.db");
        BTreeeplus tree = new BTreeeplus();

        System.out.println("=== BUILD DATA ===");

        long startBuild = System.nanoTime();

        for (int i = 0; i < total; i++) {

            Row row = new Row(i, "Name" + i);

            long offset = table.Insert(row);

            tree.Add(i, offset);
        }

        long endBuild = System.nanoTime();

        System.out.println(
                "Build time: " +
                        ((endBuild - startBuild) / 1_000_000.0) +
                        " ms"
        );


        System.out.println();
        System.out.println("=== SEQUENTIAL SCAN ===");

        long startSequential = System.nanoTime();

        ArrayList<Row> sequentialResults =
                table.getRowsBetween(min, max);

        long endSequential = System.nanoTime();

        System.out.println(
                "Found: " +
                        sequentialResults.size()
        );

        System.out.println(
                "Sequential time: " +
                        ((endSequential - startSequential) / 1_000_000.0) +
                        " ms"
        );


        System.out.println();
        System.out.println("=== B+ TREE ===");

        long startTree = System.nanoTime();

        ArrayList<Long> offsets =
                tree.FindBetween(min, max);

        ArrayList<Row> treeResults =
                table.getRows(offsets);

        long endTree = System.nanoTime();

        System.out.println(
                "Found: " +
                        treeResults.size()
        );

        System.out.println(
                "B+ Tree + file read: " +
                        ((endTree - startTree) / 1_000_000.0) +
                        " ms"
        );


        System.out.println();
        System.out.println("=== COMPARISON ===");

        double sequentialMs =
                (endSequential - startSequential) / 1_000_000.0;

        double treeMs =
                (endTree - startTree) / 1_000_000.0;

        System.out.println(
                "Sequential: " +
                        sequentialMs +
                        " ms"
        );

        System.out.println(
                "B+ Tree: " +
                        treeMs +
                        " ms"
        );

        System.out.println(
                "Speedup: " +
                        (sequentialMs / treeMs) +
                        "x"
        );
    }
}