package inc.donau.storage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import inc.donau.storage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MeasurementUnitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeasurementUnitDTO.class);
        MeasurementUnitDTO measurementUnitDTO1 = new MeasurementUnitDTO();
        measurementUnitDTO1.setId(1L);
        MeasurementUnitDTO measurementUnitDTO2 = new MeasurementUnitDTO();
        assertThat(measurementUnitDTO1).isNotEqualTo(measurementUnitDTO2);
        measurementUnitDTO2.setId(measurementUnitDTO1.getId());
        assertThat(measurementUnitDTO1).isEqualTo(measurementUnitDTO2);
        measurementUnitDTO2.setId(2L);
        assertThat(measurementUnitDTO1).isNotEqualTo(measurementUnitDTO2);
        measurementUnitDTO1.setId(null);
        assertThat(measurementUnitDTO1).isNotEqualTo(measurementUnitDTO2);
    }
}
