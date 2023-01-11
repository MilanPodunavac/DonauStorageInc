import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TransferDocument from './transfer-document';
import TransferDocumentDetail from './transfer-document-detail';
import TransferDocumentUpdate from './transfer-document-update';
import TransferDocumentDeleteDialog from './transfer-document-delete-dialog';

const TransferDocumentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TransferDocument />} />
    <Route path="new" element={<TransferDocumentUpdate />} />
    <Route path=":id">
      <Route index element={<TransferDocumentDetail />} />
      <Route path="edit" element={<TransferDocumentUpdate />} />
      <Route path="delete" element={<TransferDocumentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TransferDocumentRoutes;
