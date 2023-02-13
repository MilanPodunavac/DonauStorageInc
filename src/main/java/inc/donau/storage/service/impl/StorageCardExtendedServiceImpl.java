package inc.donau.storage.service.impl;

import inc.donau.storage.domain.StorageCard;
import inc.donau.storage.repository.StorageCardExtendedRepository;
import inc.donau.storage.repository.StorageCardRepository;
import inc.donau.storage.service.BusinessYearService;
import inc.donau.storage.service.StorageCardExtendedService;
import inc.donau.storage.service.StorageCardQueryExtendedService;
import inc.donau.storage.service.StorageCardTrafficExtendedService;
import inc.donau.storage.service.criteria.ResourceCriteria;
import inc.donau.storage.service.criteria.StorageCardCriteria;
import inc.donau.storage.service.criteria.StorageCriteria;
import inc.donau.storage.service.dto.ResourceDTO;
import inc.donau.storage.service.dto.StorageCardDTO;
import inc.donau.storage.service.dto.StorageCardTrafficDTO;
import inc.donau.storage.service.dto.StorageDTO;
import inc.donau.storage.service.mapper.StorageCardMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.LongFilter;

@Service
@Transactional
@Primary
public class StorageCardExtendedServiceImpl extends StorageCardServiceImpl implements StorageCardExtendedService {

    private final Logger log = LoggerFactory.getLogger(BusinessYearService.class);

    private final StorageCardExtendedRepository storageCardExtendedRepository;

    private final StorageCardQueryExtendedService storageCardQueryExtendedService;
    private final StorageCardTrafficExtendedService storageCardTrafficExtendedService;

    public StorageCardExtendedServiceImpl(
        StorageCardExtendedRepository storageCardRepository,
        StorageCardMapper storageCardMapper,
        StorageCardQueryExtendedService storageCardQueryExtendedService,
        StorageCardTrafficExtendedService storageCardTrafficExtendedService
    ) {
        super(storageCardRepository, storageCardMapper);
        this.storageCardExtendedRepository = storageCardRepository;
        this.storageCardQueryExtendedService = storageCardQueryExtendedService;
        this.storageCardTrafficExtendedService = storageCardTrafficExtendedService;
    }

    @Override
    public StorageCardDTO save(StorageCardDTO storageCardDTO) {
        StorageCardDTO newStorageCardDTO = super.save(storageCardDTO);

        List<StorageCardDTO> storageCardDTOList = storageCardQueryExtendedService.findByCriteria(new StorageCardCriteria());

        storageCardTrafficExtendedService.save(new StorageCardTrafficDTO(newStorageCardDTO));

        return newStorageCardDTO;
    }
}
