package inc.donau.storage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BusinessYearDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessYearDTO.class);
        BusinessYearDTO businessYearDTO1 = new BusinessYearDTO();
        businessYearDTO1.setId(1L);
        BusinessYearDTO businessYearDTO2 = new BusinessYearDTO();
        assertThat(businessYearDTO1).isNotEqualTo(businessYearDTO2);
        businessYearDTO2.setId(businessYearDTO1.getId());
        assertThat(businessYearDTO1).isEqualTo(businessYearDTO2);
        businessYearDTO2.setId(2L);
        assertThat(businessYearDTO1).isNotEqualTo(businessYearDTO2);
        businessYearDTO1.setId(null);
        assertThat(businessYearDTO1).isNotEqualTo(businessYearDTO2);
    }
}
