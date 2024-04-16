import java.util.HashMap;
import java.util.Map;

/**
 * The different types of Gopher resources.
 * <p>
 * Note this does not include everything specified by the RFC, only
 * what is necessary to complete the assignment. All other types throw an
 * IllegalArgumentException that is dealt with higher up, such as in
 * GopherResponseProcessor
 *
 * @see GopherResponseProcessor
 */
public enum GopherItemType {

    TEXT("0"),
    MENU("1"),
    ERROR("3"),
    BINARY("9"),
    INFO("i");

    public static final Map<String, GopherItemType> itemTypeMap = new HashMap<>();

    static {
        for (GopherItemType itemType : GopherItemType.values()) {
            itemTypeMap.put(itemType.label, itemType);
        }
    }


    public final String label;

    GopherItemType(String label) {
        this.label = label;
    }

    public static GopherItemType fromLabel(String label) throws IllegalArgumentException {
        if (!itemTypeMap.containsKey(label)) {
            throw new IllegalArgumentException("Invalid item type string");
        }
        return itemTypeMap.get(label);
    }


}
