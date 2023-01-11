package inc.donau.storage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BusinessContactDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessContactDTO.class);
        BusinessContactDTO businessContactDTO1 = new BusinessContactDTO();
        businessContactDTO1.setId(1L);
        BusinessContactDTO businessContactDTO2 = new BusinessContactDTO();
        assertThat(businessContactDTO1).isNotEqualTo(businessContactDTO2);
        businessContactDTO2.setId(businessContactDTO1.getId());
        assertThat(businessContactDTO1).isEqualTo(businessContactDTO2);
        businessContactDTO2.setId(2L);
        assertThat(businessContactDTO1).isNotEqualTo(businessContactDTO2);
        businessContactDTO1.setId(null);
        assertThat(businessContactDTO1).isNotEqualTo(businessContactDTO2);
    }
}
