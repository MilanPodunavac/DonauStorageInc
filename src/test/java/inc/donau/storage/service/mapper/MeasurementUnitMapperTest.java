package inc.donau.storage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MeasurementUnitMapperTest {

    private MeasurementUnitMapper measurementUnitMapper;

    @BeforeEach
    public void setUp() {
        measurementUnitMapper = new MeasurementUnitMapperImpl();
    }
}
