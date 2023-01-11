package inc.donau.storage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StorageCardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StorageCard.class);
        StorageCard storageCard1 = new StorageCard();
        storageCard1.setId("id1");
        StorageCard storageCard2 = new StorageCard();
        storageCard2.setId(storageCard1.getId());
        assertThat(storageCard1).isEqualTo(storageCard2);
        storageCard2.setId("id2");
        assertThat(storageCard1).isNotEqualTo(storageCard2);
        storageCard1.setId(null);
        assertThat(storageCard1).isNotEqualTo(storageCard2);
    }
}
