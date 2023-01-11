import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ContactInfo from './contact-info';
import ContactInfoDetail from './contact-info-detail';
import ContactInfoUpdate from './contact-info-update';
import ContactInfoDeleteDialog from './contact-info-delete-dialog';

const ContactInfoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ContactInfo />} />
    <Route path="new" element={<ContactInfoUpdate />} />
    <Route path=":id">
      <Route index element={<ContactInfoDetail />} />
      <Route path="edit" element={<ContactInfoUpdate />} />
      <Route path="delete" element={<ContactInfoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ContactInfoRoutes;
