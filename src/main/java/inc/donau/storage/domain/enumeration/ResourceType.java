package inc.donau.storage.domain.enumeration;

/**
 * The ResourceType enumeration.
 */
public enum ResourceType {
    MATERIAL("Material"),
    PRODUCT("Product"),
    HALF_PRODUCT("Half_product"),
    ENERGY_SOURCE("Energy_source"),
    WRITE_OFF("Write_off");

    private final String value;

    ResourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
