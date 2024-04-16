public class GopherResponseProcessor {

    /**
     * Convert a line of a Gopher menu into a GopherRow DTO.
     *
     * @param line a single line of response from a Gopher, should not be empty
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
        // There should be at least four tokens, since the itemType has already been extracted above.
        // Anything extra is considered extraneous and thrown away.
        if (menuLine.length < 4) {
            throw new IllegalArgumentException("Invalid gopher menu line");
        }
        // Ensure port field is an integer
        int port = Integer.parseInt(menuLine[3]);

        return new GopherRow(itemType, menuLine[0], menuLine[1], menuLine[2], port);
    }

    /**
     * Convert a response string of a menu into a GopherMenu object.
     * <p>
     * Ignores invalid responses.
     */
    public static GopherMenu menuStringToGopherMenu(String response) {
        GopherMenu menu = new GopherMenu();
        if (response == null | response.isEmpty()) {
            return menu;
        }
        String[] lines = response.split("\r\n|\n");
        for (String line : lines) {
            try {
                GopherRow row = menuLineToGopherRow(line);
                if (row.itemType == GopherItemType.INFO) {
                    continue;
                }
                menu.addRow(row);
            } catch (IllegalArgumentException ignored) {}

        }
        return menu;
    }

}
