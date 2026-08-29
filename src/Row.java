public class Row {
    int id;
    String name;

    public Row(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Row{id=" + id + ", name='" + name + "'}";
    }
}
