package inc.donau.storage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransferDocumentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferDocumentDTO.class);
        TransferDocumentDTO transferDocumentDTO1 = new TransferDocumentDTO();
        transferDocumentDTO1.setId(1L);
        TransferDocumentDTO transferDocumentDTO2 = new TransferDocumentDTO();
        assertThat(transferDocumentDTO1).isNotEqualTo(transferDocumentDTO2);
        transferDocumentDTO2.setId(transferDocumentDTO1.getId());
        assertThat(transferDocumentDTO1).isEqualTo(transferDocumentDTO2);
        transferDocumentDTO2.setId(2L);
        assertThat(transferDocumentDTO1).isNotEqualTo(transferDocumentDTO2);
        transferDocumentDTO1.setId(null);
        assertThat(transferDocumentDTO1).isNotEqualTo(transferDocumentDTO2);
    }
}
