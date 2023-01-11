package inc.donau.storage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LegalEntityMapperTest {

    private LegalEntityMapper legalEntityMapper;

    @BeforeEach
    public void setUp() {
        legalEntityMapper = new LegalEntityMapperImpl();
    }
}
