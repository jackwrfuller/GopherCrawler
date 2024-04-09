import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GopherResponseProcessorTest {

    /**
     * Ensure valid Gopher menu lines are parsed correctly into Gopher Rows.
     */
    @Test
    void testMenuLineToGopherRow() {
        GopherRow testRow = new GopherRow(GopherItemType.MENU, "Test", "/test", "gopher.quax.org", 70);
        String testMenuRowString = "1Test\t/test\tgopher.quax.org\t70";
        GopherRow result = GopherResponseProcessor.menuLineToGopherRow(testMenuRowString);
        assertEquals(result, testRow);

        GopherRow emptyMenu = new GopherRow(GopherItemType.INFO, "", "fake", "(NULL)", 0);
        String emptyRow = "i\tfake\t(NULL)\t0";
        result = GopherResponseProcessor.menuLineToGopherRow(emptyRow);
        assertEquals(result, emptyMenu);
    }

    /**
     * Ensure all ASCII characters are considered invalid Gopher item types unless specified
     * in the GopherItemType enum
     *
     * @see GopherItemType
     */
    @Test
    void testBadMenuRow() {
        for (int s = 32; s < 255; s++) {
            String type = Character.toString(s);
            if (GopherItemType.itemTypeMap.containsKey(type)) {
                continue;
            }
            String badString = type + "Test\t/test\tgopher.quax.org\t70";
            assertThrows(IllegalArgumentException.class, () -> GopherResponseProcessor.menuLineToGopherRow(badString));
        }
    }

    @Test
    void testPlusAtEnd() {
        String response = "0About This Server\t/About This Server.txt\tgopher.quux.org\t70\t+\n";
        GopherRow expected = new GopherRow(GopherItemType.TEXT, "About This Server", "/About This Server.txt",
                "gopher.quux.org", 70);
        GopherRow actual = GopherResponseProcessor.menuLineToGopherRow(response);
        assertEquals(expected, actual);
    }

    /**
     * Check menu response string is correctly parsed into a GopherMenu object
     */
    @Test
    void testMenuStringToGopherMenu() {
        String response = "1Pygopherd Home\t/devel/gopher/pygopherd\tgopher.quux.org\t70\n" +
                "1Quux.Org Mega Server\t/\tgopher.quux.org\t70\n";

        GopherRow row1 = new GopherRow(GopherItemType.MENU, "Pygopherd Home", "/devel/gopher/pygopherd", "gopher.quux.org", 70);
        GopherRow row2 = new GopherRow(GopherItemType.MENU, "Quux.Org Mega Server", "/", "gopher.quux.org", 70);

        List<GopherRow> rows = GopherResponseProcessor.menuStringToGopherMenu(response).getRows();

        assertEquals(row1, rows.get(0));
        assertEquals(row2, rows.get(1));
    }
}