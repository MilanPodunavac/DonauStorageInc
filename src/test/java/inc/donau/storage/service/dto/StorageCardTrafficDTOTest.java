package inc.donau.storage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StorageCardTrafficDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StorageCardTrafficDTO.class);
        StorageCardTrafficDTO storageCardTrafficDTO1 = new StorageCardTrafficDTO();
        storageCardTrafficDTO1.setId(1L);
        StorageCardTrafficDTO storageCardTrafficDTO2 = new StorageCardTrafficDTO();
        assertThat(storageCardTrafficDTO1).isNotEqualTo(storageCardTrafficDTO2);
        storageCardTrafficDTO2.setId(storageCardTrafficDTO1.getId());
        assertThat(storageCardTrafficDTO1).isEqualTo(storageCardTrafficDTO2);
        storageCardTrafficDTO2.setId(2L);
        assertThat(storageCardTrafficDTO1).isNotEqualTo(storageCardTrafficDTO2);
        storageCardTrafficDTO1.setId(null);
        assertThat(storageCardTrafficDTO1).isNotEqualTo(storageCardTrafficDTO2);
    }
}
