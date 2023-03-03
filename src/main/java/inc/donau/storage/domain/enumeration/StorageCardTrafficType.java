package inc.donau.storage.domain.enumeration;

/**
 * sr: Vrsta prometa magacinske kartice
 */
public enum StorageCardTrafficType {
    /**
     * sr: POCETNO_STANJE
     */
    STARTING_BALANCE("Starting_balance"),
    /**
     * sr: PROMET
     */
    TRANSFER("Transfer"),
    /**
     * sr: NIVELACIJA
     */
    LEVELING("Leveling"),
    /**
     * sr: KOREKCIJA
     */
    CORRECTION("Correction"),
    /**
     * sr: STORNIRANJE
     */
    REVERSAL("Reversal");

    private final String value;

    StorageCardTrafficType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
