import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LegalEntity from './legal-entity';
import LegalEntityDetail from './legal-entity-detail';
import LegalEntityUpdate from './legal-entity-update';
import LegalEntityDeleteDialog from './legal-entity-delete-dialog';

const LegalEntityRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LegalEntity />} />
    <Route path="new" element={<LegalEntityUpdate />} />
    <Route path=":id">
      <Route index element={<LegalEntityDetail />} />
      <Route path="edit" element={<LegalEntityUpdate />} />
      <Route path="delete" element={<LegalEntityDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LegalEntityRoutes;
