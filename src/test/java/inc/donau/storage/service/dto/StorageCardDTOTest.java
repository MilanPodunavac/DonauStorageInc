package inc.donau.storage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StorageCardDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StorageCardDTO.class);
        StorageCardDTO storageCardDTO1 = new StorageCardDTO();
        storageCardDTO1.setId("id1");
        StorageCardDTO storageCardDTO2 = new StorageCardDTO();
        assertThat(storageCardDTO1).isNotEqualTo(storageCardDTO2);
        storageCardDTO2.setId(storageCardDTO1.getId());
        assertThat(storageCardDTO1).isEqualTo(storageCardDTO2);
        storageCardDTO2.setId("id2");
        assertThat(storageCardDTO1).isNotEqualTo(storageCardDTO2);
        storageCardDTO1.setId(null);
        assertThat(storageCardDTO1).isNotEqualTo(storageCardDTO2);
    }
}
