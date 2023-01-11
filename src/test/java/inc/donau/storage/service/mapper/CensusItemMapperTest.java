package inc.donau.storage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CensusItemMapperTest {

    private CensusItemMapper censusItemMapper;

    @BeforeEach
    public void setUp() {
        censusItemMapper = new CensusItemMapperImpl();
    }
}
