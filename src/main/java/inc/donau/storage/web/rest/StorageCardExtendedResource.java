package inc.donau.storage.web.rest;

import static com.itextpdf.kernel.pdf.PdfName.Document;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import inc.donau.storage.domain.StorageCard;
import inc.donau.storage.repository.StorageCardExtendedRepository;
import inc.donau.storage.repository.StorageCardRepository;
import inc.donau.storage.service.*;
import inc.donau.storage.service.criteria.BusinessContactCriteria;
import inc.donau.storage.service.criteria.EmployeeCriteria;
import inc.donau.storage.service.criteria.StorageCardTrafficCriteria;
import inc.donau.storage.service.dto.BusinessContactDTO;
import inc.donau.storage.service.dto.EmployeeDTO;
import inc.donau.storage.service.dto.StorageCardDTO;
import inc.donau.storage.service.dto.StorageCardTrafficDTO;
import inc.donau.storage.web.rest.errors.BadRequestAlertException;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.dom4j.DocumentException;
import org.h2.mvstore.Chunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/xt")
public class StorageCardExtendedResource extends StorageCardResource {

    private final Logger log = LoggerFactory.getLogger(StorageCardResource.class);

    private final String baseLocationName = Paths.get("").toAbsolutePath() + "\\analytics\\";

    private static final String ENTITY_NAME = "storageCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StorageCardExtendedService storageCardExtendedService;
    private final StorageCardExtendedRepository storageCardExtendedRepository;
    private final StorageCardQueryExtendedService storageCardQueryExtendedService;

    private final StorageCardTrafficQueryExtendedService storageCardTrafficQueryExtendedService;

    public StorageCardExtendedResource(
        StorageCardExtendedService storageCardService,
        StorageCardExtendedRepository storageCardRepository,
        StorageCardQueryExtendedService storageCardQueryService,
        StorageCardTrafficQueryExtendedService storageCardTrafficQueryExtendedService
    ) {
        super(storageCardService, storageCardRepository, storageCardQueryService);
        this.storageCardExtendedService = storageCardService;
        this.storageCardExtendedRepository = storageCardRepository;
        this.storageCardQueryExtendedService = storageCardQueryService;
        this.storageCardTrafficQueryExtendedService = storageCardTrafficQueryExtendedService;
    }

    @Override
    public ResponseEntity<Void> deleteStorageCard(@PathVariable String id) {
        StringFilter stringFilter = new StringFilter();
        stringFilter.setEquals(id);
        StorageCardTrafficCriteria storageCardTrafficCriteria = new StorageCardTrafficCriteria();
        storageCardTrafficCriteria.setStorageCardId(stringFilter);
        List<StorageCardTrafficDTO> traffic = storageCardTrafficQueryExtendedService.findByCriteria(storageCardTrafficCriteria);
        if (!traffic.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "storageCardTrafficAsssociationStorageCard",
                    "This storage card has traffic registered," + " it cannot be deleted by law"
                )
            )
            .build();

        return super.deleteStorageCard(id);
    }

    @PutMapping("/storage-cards/{id}/analytics")
    public ResponseEntity<String> generateAnalyticsForStorageCard(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody StorageCardDTO storageCardDTO
    ) throws IOException {
        log.debug("REST request to generate analytics for StorageCard : {}", id);

        if (storageCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storageCardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!storageCardExtendedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        String fileName = "Storage_card_analytics-" + storageCardDTO.getId() + "-" + LocalDate.now() + ".pdf";

        String destination = baseLocationName + fileName;

        String html = getHTMLDocument(storageCardDTO);

        HtmlConverter.convertToPdf(html, new FileOutputStream(destination));

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, storageCardDTO.getId()))
            .body(destination);
    }

    private String getHTMLDocument(StorageCardDTO storageCardDTO) {
        String html =
            "<h1>Storage Card</h1>" +
            "<p>" +
            LocalDate.now() +
            "</p>" +
            "<br>" +
            "<p><b>Business Year: </b>" +
            storageCardDTO.getBusinessYear().getYearCode() +
            "</p>" +
            "<br>" +
            "<p><b>Storage: </b>" +
            storageCardDTO.getStorage().getName() +
            "</p>" +
            "<p>Address: " +
            storageCardDTO.getStorage().getAddress().getStreetName() +
            " " +
            storageCardDTO.getStorage().getAddress().getStreetCode() +
            ", " +
            storageCardDTO.getStorage().getAddress().getCity().getName() +
            "</p>" +
            "<br>" +
            "<p><b>Resource: </b>" +
            storageCardDTO.getResource().getName() +
            "</p>" +
            "<p>Type: " +
            storageCardDTO.getResource().getType() +
            "</p>" +
            "<p>Source: " +
            storageCardDTO.getBusinessYear().getCompany().getLegalEntityInfo().getName() +
            "</p>" +
            "<p>Price: " +
            storageCardDTO.getPrice() +
            "</p>" +
            "<table><tr><th></th><th><b>Amount</b></th><th><b>Value</b></th></tr>" +
            "<tr><td>Starting</td><td>" +
            storageCardDTO.getStartingAmount() +
            "</td><td>" +
            storageCardDTO.getStartingValue() +
            "</td></tr>" +
            "<tr><td>Received</td><td>" +
            storageCardDTO.getReceivedAmount() +
            "</td><td>" +
            storageCardDTO.getReceivedValue() +
            "</td></tr>" +
            "<tr><td>Dispatched</td><td>" +
            storageCardDTO.getDispatchedAmount() +
            "</td><td>" +
            storageCardDTO.getDispatchedValue() +
            "</td></tr>" +
            "<tr><td>Total</td><td>" +
            storageCardDTO.getTotalAmount() +
            "</td><td>" +
            storageCardDTO.getTotalValue() +
            "</td></tr></table>" +
            "<h2>Traffic</h2>" +
            "<table><tr><th><b>Document</b></th>" +
            "<th><b>Date</b></th>" +
            "<th><b>Type</b></th>" +
            "<th><b>Direction</b></th>" +
            "<th><b>Amount</b></th>" +
            "<th><b>Price</b></th>" +
            "<th><b>Value</b></th></tr>";

        StorageCardTrafficCriteria trafficCriteria = new StorageCardTrafficCriteria();
        StringFilter cardFilter = new StringFilter();
        cardFilter.setEquals(storageCardDTO.getId());
        trafficCriteria.setStorageCardId(cardFilter);
        for (StorageCardTrafficDTO trafficDTO : storageCardTrafficQueryExtendedService.findByCriteria(trafficCriteria)) {
            html +=
                "<tr><td>" +
                (trafficDTO.getDocument() != null ? trafficDTO.getDocument() : "") +
                "</td><td>" +
                trafficDTO.getDate() +
                "</td><td>" +
                trafficDTO.getType() +
                "</td><td>" +
                trafficDTO.getDirection() +
                "</td><td>" +
                trafficDTO.getAmount() +
                "</td><td>" +
                trafficDTO.getPrice() +
                "</td><td>" +
                (trafficDTO.getAmount() * trafficDTO.getPrice()) +
                "</td><td></tr>";
        }

        html += "</table>";
        return html;
    }
}
