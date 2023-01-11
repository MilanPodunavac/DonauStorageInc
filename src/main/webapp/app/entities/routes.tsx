import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Country from './country';
import City from './city';
import Address from './address';
import Person from './person';
import LegalEntity from './legal-entity';
import Employee from './employee';
import ContactInfo from './contact-info';
import BusinessPartner from './business-partner';
import BusinessContact from './business-contact';
import Company from './company';
import BusinessYear from './business-year';
import Resource from './resource';
import MeasurementUnit from './measurement-unit';
import Storage from './storage';
import TransferDocument from './transfer-document';
import TransferDocumentItem from './transfer-document-item';
import StorageCard from './storage-card';
import StorageCardTraffic from './storage-card-traffic';
import CensusDocument from './census-document';
import CensusItem from './census-item';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="country/*" element={<Country />} />
        <Route path="city/*" element={<City />} />
        <Route path="address/*" element={<Address />} />
        <Route path="person/*" element={<Person />} />
        <Route path="legal-entity/*" element={<LegalEntity />} />
        <Route path="employee/*" element={<Employee />} />
        <Route path="contact-info/*" element={<ContactInfo />} />
        <Route path="business-partner/*" element={<BusinessPartner />} />
        <Route path="business-contact/*" element={<BusinessContact />} />
        <Route path="company/*" element={<Company />} />
        <Route path="business-year/*" element={<BusinessYear />} />
        <Route path="resource/*" element={<Resource />} />
        <Route path="measurement-unit/*" element={<MeasurementUnit />} />
        <Route path="storage/*" element={<Storage />} />
        <Route path="transfer-document/*" element={<TransferDocument />} />
        <Route path="transfer-document-item/*" element={<TransferDocumentItem />} />
        <Route path="storage-card/*" element={<StorageCard />} />
        <Route path="storage-card-traffic/*" element={<StorageCardTraffic />} />
        <Route path="census-document/*" element={<CensusDocument />} />
        <Route path="census-item/*" element={<CensusItem />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
