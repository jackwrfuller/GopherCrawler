public class GopherResponseProcessor {

    /**
     * Convert a line of a Gopher menu into a GopherRow DTO
     * @param line a single line of response from a Gopher
     * @return the menu row
     */
    public static GopherRow menuLineToGopherRow(String line) throws IllegalArgumentException {
        // The first character is always the Gopher item type
        StringBuilder sb = new StringBuilder(line);
        String itemTypeString = sb.charAt(0) + "";
        GopherItemType itemType = GopherItemType.fromLabel(itemTypeString);
        sb.deleteCharAt(0);

        // Tokenise by tabs
        String[] menuLine = sb.toString().split("\t");
        // There should be four tokens, since the itemType has already been extracted above.
        if (menuLine.length != 4) {
            throw new IllegalArgumentException("Invalid gopher menu line");
        }
        return new GopherRow(itemType, menuLine[0], menuLine[1], menuLine[2], menuLine[3]);
    }
}
