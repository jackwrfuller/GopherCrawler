import java.util.HashMap;
import java.util.Map;

public enum GopherItemType {

    TEXT("0"),
    MENU("1"),
    ERROR("3"),
    BINARY("9"),
    HTML("h"),
    INFO("i");

    private static final Map<String, GopherItemType> itemTypeMap = new HashMap<>();

    static {
        for (GopherItemType itemType : GopherItemType.values()) {
            itemTypeMap.put(itemType.label, itemType);
        }
    }


    public final String label;

    GopherItemType(String label) {
        this.label = label;
    }

    public static GopherItemType fromLabel(String label) {
        return itemTypeMap.get(label);
    }


}
