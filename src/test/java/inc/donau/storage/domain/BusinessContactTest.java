package inc.donau.storage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BusinessContactTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessContact.class);
        BusinessContact businessContact1 = new BusinessContact();
        businessContact1.setId(1L);
        BusinessContact businessContact2 = new BusinessContact();
        businessContact2.setId(businessContact1.getId());
        assertThat(businessContact1).isEqualTo(businessContact2);
        businessContact2.setId(2L);
        assertThat(businessContact1).isNotEqualTo(businessContact2);
        businessContact1.setId(null);
        assertThat(businessContact1).isNotEqualTo(businessContact2);
    }
}
