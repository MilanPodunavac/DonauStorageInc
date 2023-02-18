import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TransferDocumentItem from './transfer-document-item';
import TransferDocumentItemDetail from './transfer-document-item-detail';
import TransferDocumentItemUpdate from './transfer-document-item-update';
import TransferDocumentItemDeleteDialog from './transfer-document-item-delete-dialog';

const TransferDocumentItemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TransferDocumentItem />} />
    <Route path="/new/:documentId" element={<TransferDocumentItemUpdate />} />
    <Route path=":id">
      <Route index element={<TransferDocumentItemDetail />} />
      <Route path="edit" element={<TransferDocumentItemUpdate />} />
      <Route path="delete" element={<TransferDocumentItemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TransferDocumentItemRoutes;
