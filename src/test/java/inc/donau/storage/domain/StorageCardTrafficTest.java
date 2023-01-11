package inc.donau.storage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StorageCardTrafficTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StorageCardTraffic.class);
        StorageCardTraffic storageCardTraffic1 = new StorageCardTraffic();
        storageCardTraffic1.setId(1L);
        StorageCardTraffic storageCardTraffic2 = new StorageCardTraffic();
        storageCardTraffic2.setId(storageCardTraffic1.getId());
        assertThat(storageCardTraffic1).isEqualTo(storageCardTraffic2);
        storageCardTraffic2.setId(2L);
        assertThat(storageCardTraffic1).isNotEqualTo(storageCardTraffic2);
        storageCardTraffic1.setId(null);
        assertThat(storageCardTraffic1).isNotEqualTo(storageCardTraffic2);
    }
}
