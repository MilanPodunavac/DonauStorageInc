import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BusinessContact from './business-contact';
import BusinessContactDetail from './business-contact-detail';
import BusinessContactUpdate from './business-contact-update';
import BusinessContactDeleteDialog from './business-contact-delete-dialog';

const BusinessContactRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BusinessContact />} />
    <Route path="new" element={<BusinessContactUpdate />} />
    <Route path=":id">
      <Route index element={<BusinessContactDetail />} />
      <Route path="edit" element={<BusinessContactUpdate />} />
      <Route path="delete" element={<BusinessContactDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BusinessContactRoutes;
