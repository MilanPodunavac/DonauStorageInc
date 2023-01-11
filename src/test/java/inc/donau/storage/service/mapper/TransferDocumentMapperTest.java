package inc.donau.storage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransferDocumentMapperTest {

    private TransferDocumentMapper transferDocumentMapper;

    @BeforeEach
    public void setUp() {
        transferDocumentMapper = new TransferDocumentMapperImpl();
    }
}
