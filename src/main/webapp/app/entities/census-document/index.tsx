import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CensusDocument from './census-document';
import CensusDocumentDetail from './census-document-detail';
import CensusDocumentUpdate from './census-document-update';
import CensusDocumentDeleteDialog from './census-document-delete-dialog';

const CensusDocumentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CensusDocument />} />
    <Route path="new" element={<CensusDocumentUpdate />} />
    <Route path=":id">
      <Route index element={<CensusDocumentDetail />} />
      <Route path="edit" element={<CensusDocumentUpdate />} />
      <Route path="delete" element={<CensusDocumentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CensusDocumentRoutes;
