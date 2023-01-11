package inc.donau.storage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageCardTrafficMapperTest {

    private StorageCardTrafficMapper storageCardTrafficMapper;

    @BeforeEach
    public void setUp() {
        storageCardTrafficMapper = new StorageCardTrafficMapperImpl();
    }
}
