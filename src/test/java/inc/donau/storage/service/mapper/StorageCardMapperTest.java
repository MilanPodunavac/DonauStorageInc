package inc.donau.storage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageCardMapperTest {

    private StorageCardMapper storageCardMapper;

    @BeforeEach
    public void setUp() {
        storageCardMapper = new StorageCardMapperImpl();
    }
}
