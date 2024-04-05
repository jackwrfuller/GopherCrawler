public class GopherResponseProcessor {

    /**
     * Convert a line of a Gopher menu into a GopherRow DTO
     * @param line a single line of response from a Gopher
     * @return the menu row
     */
    public static GopherRow menuLineToGopherRow(String line) throws IllegalArgumentException {
        // The first character is always the Gopher item type
        StringBuilder sb = new StringBuilder(line);
        String itemType = sb.charAt(0) + "";
        sb.deleteCharAt(0);

        // Tokenise by tabs
        String[] data = sb.toString().split("\t");
        if (data.length < 1) {
            throw new IllegalArgumentException("Invalid gopher menu line");
        }


    }
}
