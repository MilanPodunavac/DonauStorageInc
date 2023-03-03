package inc.donau.storage.domain.enumeration;

/**
 * sr: Vrsta prometnog dokumenta
 */
public enum TransferDocumentType {
    /**
     * sr: PRIMKA
     */
    RECEIVING("Receiving"),
    /**
     * sr: OTPREMNICA
     */
    DISPATCHING("Dispatching"),
    /**
     * sr: MEDJUMAGACINSKO POSLOVANJE
     */
    INTERSTORAGE("Interstorage");

    private final String value;

    TransferDocumentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
