package inc.donau.storage.service;

import inc.donau.storage.repository.ContactInfoExtendedRepository;
import inc.donau.storage.repository.ContactInfoRepository;
import inc.donau.storage.service.mapper.ContactInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContactInfoExtendedService extends ContactInfoService {

    private final ContactInfoExtendedRepository contactInfoExtendedRepository;

    public ContactInfoExtendedService(ContactInfoExtendedRepository contactInfoRepository, ContactInfoMapper contactInfoMapper) {
        super(contactInfoRepository, contactInfoMapper);
        this.contactInfoExtendedRepository = contactInfoRepository;
    }
}
