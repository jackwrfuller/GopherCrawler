import java.util.ArrayList;
import java.util.List;

/**
 * A DTO representing a Gopher menu returned by a server.
 */
public class GopherMenu{

    private final List<GopherRow> rows;

    public GopherMenu() {
        rows = new ArrayList<>();
    }

    public void addRow(GopherRow row) {
        rows.add(row);
    }

    public List<GopherRow> getRows() {
        return rows;
    }

}
