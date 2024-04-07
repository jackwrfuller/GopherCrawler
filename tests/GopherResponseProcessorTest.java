import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GopherResponseProcessorTest {

    @Test
    void menuLineToGopherRow() {
        GopherRow testRow = new GopherRow(GopherItemType.MENU, "Test", "/test", "gopher.quax.org", "70");
        String testMenuRowString = "1Test\t/test\tgopher.quax.org\t70";
        GopherRow result = GopherResponseProcessor.menuLineToGopherRow(testMenuRowString);
        assertEquals(result, testRow);

        GopherRow emptyMenu = new GopherRow(GopherItemType.INFO, "", "fake", "(NULL)", "0");
        String emptyRow = "i\tfake\t(NULL)\t0";
        result = GopherResponseProcessor.menuLineToGopherRow(emptyRow);
        assertEquals(result, emptyMenu);
    }
}