package inc.donau.storage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BusinessYearTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessYear.class);
        BusinessYear businessYear1 = new BusinessYear();
        businessYear1.setId(1L);
        BusinessYear businessYear2 = new BusinessYear();
        businessYear2.setId(businessYear1.getId());
        assertThat(businessYear1).isEqualTo(businessYear2);
        businessYear2.setId(2L);
        assertThat(businessYear1).isNotEqualTo(businessYear2);
        businessYear1.setId(null);
        assertThat(businessYear1).isNotEqualTo(businessYear2);
    }
}
