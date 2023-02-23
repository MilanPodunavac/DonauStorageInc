import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StorageCard from './storage-card';
import StorageCardDetail from './storage-card-detail';
import StorageCardUpdate from './storage-card-update';
import StorageCardDeleteDialog from './storage-card-delete-dialog';

const StorageCardRoutes = chosenBusinessYear => (
  <ErrorBoundaryRoutes>
    <Route index element={<StorageCard />} />
    <Route path="new" element={<StorageCardUpdate />} />
    <Route path=":id">
      <Route index element={<StorageCardDetail />} />
      <Route path="edit" element={<StorageCardUpdate />} />
      <Route path="delete" element={<StorageCardDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StorageCardRoutes;
