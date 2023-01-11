import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BusinessPartner from './business-partner';
import BusinessPartnerDetail from './business-partner-detail';
import BusinessPartnerUpdate from './business-partner-update';
import BusinessPartnerDeleteDialog from './business-partner-delete-dialog';

const BusinessPartnerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BusinessPartner />} />
    <Route path="new" element={<BusinessPartnerUpdate />} />
    <Route path=":id">
      <Route index element={<BusinessPartnerDetail />} />
      <Route path="edit" element={<BusinessPartnerUpdate />} />
      <Route path="delete" element={<BusinessPartnerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BusinessPartnerRoutes;
