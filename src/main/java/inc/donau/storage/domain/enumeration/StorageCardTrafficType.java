package inc.donau.storage.domain.enumeration;

/**
 * sr: Vrsta prometa magacinske kartice
 */
public enum StorageCardTrafficType {
    /**
     * sr: POCETNO_STANJE
     */
    STARTING_BALANCE,
    /**
     * sr: PROMET
     */
    TRANSFER,
    /**
     * sr: NIVELACIJA
     */
    LEVELING,
    /**
     * sr: KOREKCIJA
     */
    CORRECTION,
    /**
     * sr: STORNIRANJE
     */
    REVERSAL,
}
