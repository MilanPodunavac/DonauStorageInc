import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CensusItem from './census-item';
import CensusItemDetail from './census-item-detail';
import CensusItemUpdate from './census-item-update';
import CensusItemDeleteDialog from './census-item-delete-dialog';

const CensusItemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CensusItem />} />
    <Route path="new" element={<CensusItemUpdate />} />
    <Route path=":id">
      <Route index element={<CensusItemDetail />} />
      <Route path="edit" element={<CensusItemUpdate />} />
      <Route path="delete" element={<CensusItemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CensusItemRoutes;
