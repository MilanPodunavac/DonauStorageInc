package inc.donau.storage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CensusDocumentMapperTest {

    private CensusDocumentMapper censusDocumentMapper;

    @BeforeEach
    public void setUp() {
        censusDocumentMapper = new CensusDocumentMapperImpl();
    }
}
