package inc.donau.storage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BusinessPartnerMapperTest {

    private BusinessPartnerMapper businessPartnerMapper;

    @BeforeEach
    public void setUp() {
        businessPartnerMapper = new BusinessPartnerMapperImpl();
    }
}
