package inc.donau.storage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactInfoMapperTest {

    private ContactInfoMapper contactInfoMapper;

    @BeforeEach
    public void setUp() {
        contactInfoMapper = new ContactInfoMapperImpl();
    }
}
