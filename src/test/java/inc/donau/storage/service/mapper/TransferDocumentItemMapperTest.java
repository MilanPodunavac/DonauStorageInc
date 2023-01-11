package inc.donau.storage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransferDocumentItemMapperTest {

    private TransferDocumentItemMapper transferDocumentItemMapper;

    @BeforeEach
    public void setUp() {
        transferDocumentItemMapper = new TransferDocumentItemMapperImpl();
    }
}
