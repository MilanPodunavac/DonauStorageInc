package inc.donau.storage.domain.enumeration;

/**
 * sr: Status prometnog dokumenta
 */
public enum TransferDocumentStatus {
    /**
     * sr: U IZRADI
     */
    IN_PREPARATION("In_preparation"),
    /**
     * sr: PROKNJIZEN
     */
    ACCOUNTED("Accounted"),
    /**
     * sr: STORNIRAN
     */
    REVERSED("Reversed");

    private final String value;

    TransferDocumentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
