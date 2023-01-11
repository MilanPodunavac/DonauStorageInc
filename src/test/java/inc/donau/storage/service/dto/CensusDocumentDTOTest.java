package inc.donau.storage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CensusDocumentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CensusDocumentDTO.class);
        CensusDocumentDTO censusDocumentDTO1 = new CensusDocumentDTO();
        censusDocumentDTO1.setId(1L);
        CensusDocumentDTO censusDocumentDTO2 = new CensusDocumentDTO();
        assertThat(censusDocumentDTO1).isNotEqualTo(censusDocumentDTO2);
        censusDocumentDTO2.setId(censusDocumentDTO1.getId());
        assertThat(censusDocumentDTO1).isEqualTo(censusDocumentDTO2);
        censusDocumentDTO2.setId(2L);
        assertThat(censusDocumentDTO1).isNotEqualTo(censusDocumentDTO2);
        censusDocumentDTO1.setId(null);
        assertThat(censusDocumentDTO1).isNotEqualTo(censusDocumentDTO2);
    }
}
