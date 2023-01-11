package inc.donau.storage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactInfoDTO.class);
        ContactInfoDTO contactInfoDTO1 = new ContactInfoDTO();
        contactInfoDTO1.setId(1L);
        ContactInfoDTO contactInfoDTO2 = new ContactInfoDTO();
        assertThat(contactInfoDTO1).isNotEqualTo(contactInfoDTO2);
        contactInfoDTO2.setId(contactInfoDTO1.getId());
        assertThat(contactInfoDTO1).isEqualTo(contactInfoDTO2);
        contactInfoDTO2.setId(2L);
        assertThat(contactInfoDTO1).isNotEqualTo(contactInfoDTO2);
        contactInfoDTO1.setId(null);
        assertThat(contactInfoDTO1).isNotEqualTo(contactInfoDTO2);
    }
}
