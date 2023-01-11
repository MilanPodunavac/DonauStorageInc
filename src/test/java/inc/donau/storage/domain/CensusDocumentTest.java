package inc.donau.storage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CensusDocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CensusDocument.class);
        CensusDocument censusDocument1 = new CensusDocument();
        censusDocument1.setId(1L);
        CensusDocument censusDocument2 = new CensusDocument();
        censusDocument2.setId(censusDocument1.getId());
        assertThat(censusDocument1).isEqualTo(censusDocument2);
        censusDocument2.setId(2L);
        assertThat(censusDocument1).isNotEqualTo(censusDocument2);
        censusDocument1.setId(null);
        assertThat(censusDocument1).isNotEqualTo(censusDocument2);
    }
}
