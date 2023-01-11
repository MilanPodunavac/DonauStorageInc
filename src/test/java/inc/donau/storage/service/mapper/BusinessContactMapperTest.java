package inc.donau.storage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BusinessContactMapperTest {

    private BusinessContactMapper businessContactMapper;

    @BeforeEach
    public void setUp() {
        businessContactMapper = new BusinessContactMapperImpl();
    }
}
