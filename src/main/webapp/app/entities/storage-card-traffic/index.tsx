import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StorageCardTraffic from './storage-card-traffic';
import StorageCardTrafficDetail from './storage-card-traffic-detail';
import StorageCardTrafficUpdate from './storage-card-traffic-update';
import StorageCardTrafficDeleteDialog from './storage-card-traffic-delete-dialog';

const StorageCardTrafficRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StorageCardTraffic />} />
    <Route path="new/:cardId" element={<StorageCardTrafficUpdate />} />
    <Route path=":id">
      <Route index element={<StorageCardTrafficDetail />} />
      <Route path="edit" element={<StorageCardTrafficUpdate />} />
      <Route path="delete" element={<StorageCardTrafficDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StorageCardTrafficRoutes;
