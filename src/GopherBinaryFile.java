public class GopherBinaryFile extends GopherTreeNodeType {

    private final String binFile;

    public GopherBinaryFile(String binFile) {
        this.binFile = binFile;
    }

    public String getTextFile() {
        return binFile;
    }
}
