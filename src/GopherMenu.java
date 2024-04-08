import java.util.ArrayList;
import java.util.List;

public class GopherMenu extends GopherTreeNodeType {

    private List<GopherRow> rows;

    public GopherMenu() {
        rows = new ArrayList<>();
    }

    public boolean addRow(GopherRow row) {
        return rows.add(row);
    }

    public void setRows(List<GopherRow> rows) {
        this.rows = rows;
    }

    public List<GopherRow> getRows() {
        return rows;
    }

}
