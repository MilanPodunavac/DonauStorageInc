package inc.donau.storage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BusinessYearMapperTest {

    private BusinessYearMapper businessYearMapper;

    @BeforeEach
    public void setUp() {
        businessYearMapper = new BusinessYearMapperImpl();
    }
}
