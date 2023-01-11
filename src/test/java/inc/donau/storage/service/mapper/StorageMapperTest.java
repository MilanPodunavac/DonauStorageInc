package inc.donau.storage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageMapperTest {

    private StorageMapper storageMapper;

    @BeforeEach
    public void setUp() {
        storageMapper = new StorageMapperImpl();
    }
}
