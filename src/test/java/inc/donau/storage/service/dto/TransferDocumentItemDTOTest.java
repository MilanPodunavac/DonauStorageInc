package inc.donau.storage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransferDocumentItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferDocumentItemDTO.class);
        TransferDocumentItemDTO transferDocumentItemDTO1 = new TransferDocumentItemDTO();
        transferDocumentItemDTO1.setId(1L);
        TransferDocumentItemDTO transferDocumentItemDTO2 = new TransferDocumentItemDTO();
        assertThat(transferDocumentItemDTO1).isNotEqualTo(transferDocumentItemDTO2);
        transferDocumentItemDTO2.setId(transferDocumentItemDTO1.getId());
        assertThat(transferDocumentItemDTO1).isEqualTo(transferDocumentItemDTO2);
        transferDocumentItemDTO2.setId(2L);
        assertThat(transferDocumentItemDTO1).isNotEqualTo(transferDocumentItemDTO2);
        transferDocumentItemDTO1.setId(null);
        assertThat(transferDocumentItemDTO1).isNotEqualTo(transferDocumentItemDTO2);
    }
}
