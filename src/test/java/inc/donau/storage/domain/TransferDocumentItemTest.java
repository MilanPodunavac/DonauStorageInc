package inc.donau.storage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransferDocumentItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferDocumentItem.class);
        TransferDocumentItem transferDocumentItem1 = new TransferDocumentItem();
        transferDocumentItem1.setId(1L);
        TransferDocumentItem transferDocumentItem2 = new TransferDocumentItem();
        transferDocumentItem2.setId(transferDocumentItem1.getId());
        assertThat(transferDocumentItem1).isEqualTo(transferDocumentItem2);
        transferDocumentItem2.setId(2L);
        assertThat(transferDocumentItem1).isNotEqualTo(transferDocumentItem2);
        transferDocumentItem1.setId(null);
        assertThat(transferDocumentItem1).isNotEqualTo(transferDocumentItem2);
    }
}
