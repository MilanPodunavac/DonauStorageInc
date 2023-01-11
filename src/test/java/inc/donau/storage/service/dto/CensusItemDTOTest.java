package inc.donau.storage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CensusItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CensusItemDTO.class);
        CensusItemDTO censusItemDTO1 = new CensusItemDTO();
        censusItemDTO1.setId(1L);
        CensusItemDTO censusItemDTO2 = new CensusItemDTO();
        assertThat(censusItemDTO1).isNotEqualTo(censusItemDTO2);
        censusItemDTO2.setId(censusItemDTO1.getId());
        assertThat(censusItemDTO1).isEqualTo(censusItemDTO2);
        censusItemDTO2.setId(2L);
        assertThat(censusItemDTO1).isNotEqualTo(censusItemDTO2);
        censusItemDTO1.setId(null);
        assertThat(censusItemDTO1).isNotEqualTo(censusItemDTO2);
    }
}
