package inc.donau.storage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransferDocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferDocument.class);
        TransferDocument transferDocument1 = new TransferDocument();
        transferDocument1.setId(1L);
        TransferDocument transferDocument2 = new TransferDocument();
        transferDocument2.setId(transferDocument1.getId());
        assertThat(transferDocument1).isEqualTo(transferDocument2);
        transferDocument2.setId(2L);
        assertThat(transferDocument1).isNotEqualTo(transferDocument2);
        transferDocument1.setId(null);
        assertThat(transferDocument1).isNotEqualTo(transferDocument2);
    }
}
