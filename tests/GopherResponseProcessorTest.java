import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GopherResponseProcessorTest {

    /**
     * Ensure valid Gopher menu lines are parsed correctly into Gopher Rows.
     */
    @Test
    void testMenuLineToGopherRow() {
        GopherRow testRow = new GopherRow(GopherItemType.MENU, "Test", "/test", "gopher.quax.org", "70");
        String testMenuRowString = "1Test\t/test\tgopher.quax.org\t70";
        GopherRow result = GopherResponseProcessor.menuLineToGopherRow(testMenuRowString);
        assertEquals(result, testRow);

        GopherRow emptyMenu = new GopherRow(GopherItemType.INFO, "", "fake", "(NULL)", "0");
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
}