import country from 'app/entities/country/country.reducer';
import city from 'app/entities/city/city.reducer';
import address from 'app/entities/address/address.reducer';
import person from 'app/entities/person/person.reducer';
import legalEntity from 'app/entities/legal-entity/legal-entity.reducer';
import employee from 'app/entities/employee/employee.reducer';
import contactInfo from 'app/entities/contact-info/contact-info.reducer';
import businessPartner from 'app/entities/business-partner/business-partner.reducer';
import businessContact from 'app/entities/business-contact/business-contact.reducer';
import company from 'app/entities/company/company.reducer';
import businessYear from 'app/entities/business-year/business-year.reducer';
import resource from 'app/entities/resource/resource.reducer';
import measurementUnit from 'app/entities/measurement-unit/measurement-unit.reducer';
import storage from 'app/entities/storage/storage.reducer';
import transferDocument from 'app/entities/transfer-document/transfer-document.reducer';
import transferDocumentItem from 'app/entities/transfer-document-item/transfer-document-item.reducer';
import storageCard from 'app/entities/storage-card/storage-card.reducer';
import storageCardTraffic from 'app/entities/storage-card-traffic/storage-card-traffic.reducer';
import censusDocument from 'app/entities/census-document/census-document.reducer';
import censusItem from 'app/entities/census-item/census-item.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  country,
  city,
  address,
  person,
  legalEntity,
  employee,
  contactInfo,
  businessPartner,
  businessContact,
  company,
  businessYear,
  resource,
  measurementUnit,
  storage,
  transferDocument,
  transferDocumentItem,
  storageCard,
  storageCardTraffic,
  censusDocument,
  censusItem,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
