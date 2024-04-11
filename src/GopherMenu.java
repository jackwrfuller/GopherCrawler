import java.util.ArrayList;
import java.util.List;

public class GopherMenu{

    private List<GopherRow> rows;

    public GopherMenu() {
        rows = new ArrayList<>();
    }

    public void addRow(GopherRow row) {
        rows.add(row);
    }

    public void setRows(List<GopherRow> rows) {
        this.rows = rows;
    }

    public List<GopherRow> getRows() {
        return rows;
    }

}
