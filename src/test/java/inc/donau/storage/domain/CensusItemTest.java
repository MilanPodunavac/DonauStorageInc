package inc.donau.storage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CensusItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CensusItem.class);
        CensusItem censusItem1 = new CensusItem();
        censusItem1.setId(1L);
        CensusItem censusItem2 = new CensusItem();
        censusItem2.setId(censusItem1.getId());
        assertThat(censusItem1).isEqualTo(censusItem2);
        censusItem2.setId(2L);
        assertThat(censusItem1).isNotEqualTo(censusItem2);
        censusItem1.setId(null);
        assertThat(censusItem1).isNotEqualTo(censusItem2);
    }
}
