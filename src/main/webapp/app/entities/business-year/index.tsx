import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BusinessYear from './business-year';
import BusinessYearDetail from './business-year-detail';
import BusinessYearUpdate from './business-year-update';
import BusinessYearDeleteDialog from './business-year-delete-dialog';

const BusinessYearRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BusinessYear />} />
    <Route path="new" element={<BusinessYearUpdate />} />
    <Route path=":id">
      <Route index element={<BusinessYearDetail />} />
      <Route path="edit" element={<BusinessYearUpdate />} />
      <Route path="delete" element={<BusinessYearDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BusinessYearRoutes;
