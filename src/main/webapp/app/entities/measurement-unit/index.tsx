import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MeasurementUnit from './measurement-unit';
import MeasurementUnitDetail from './measurement-unit-detail';
import MeasurementUnitUpdate from './measurement-unit-update';
import MeasurementUnitDeleteDialog from './measurement-unit-delete-dialog';

const MeasurementUnitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MeasurementUnit />} />
    <Route path="new" element={<MeasurementUnitUpdate />} />
    <Route path=":id">
      <Route index element={<MeasurementUnitDetail />} />
      <Route path="edit" element={<MeasurementUnitUpdate />} />
      <Route path="delete" element={<MeasurementUnitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MeasurementUnitRoutes;
