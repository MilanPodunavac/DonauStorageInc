package inc.donau.storage.domain.enumeration;

/**
 * sr: Status prometnog dokumenta
 */
public enum TransferDocumentStatus {
    /**
     * sr: U IZRADI
     */
    IN_PREPARATION,
    /**
     * sr: PROKNJIZEN
     */
    ACCOUNTED,
    /**
     * sr: STORNIRAN
     */
    REVERSED,
}
