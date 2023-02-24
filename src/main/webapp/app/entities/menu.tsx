import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

export const EntitiesMenu = ({ isAdmin }: { isAdmin: boolean }) => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/country">
        <Translate contentKey="global.menu.entities.country" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/city">
        <Translate contentKey="global.menu.entities.city" />
      </MenuItem>
      {/*
        <MenuItem icon="asterisk" to="/address">
          <Translate contentKey="global.menu.entities.address" />
        </MenuItem>
      */}
      {/*
        <MenuItem icon="asterisk" to="/person">
          <Translate contentKey="global.menu.entities.person" />
        </MenuItem>
      */}
      {/*
        <MenuItem icon="asterisk" to="/legal-entity">
          <Translate contentKey="global.menu.entities.legalEntity" />
        </MenuItem>
      */}
      <MenuItem icon="asterisk" to="/employee">
        <Translate contentKey="global.menu.entities.employee" />
      </MenuItem>
      {/*
        <MenuItem icon="asterisk" to="/contact-info">
          <Translate contentKey="global.menu.entities.contactInfo" />
        </MenuItem>
      */}
      <MenuItem icon="asterisk" to="/business-partner">
        <Translate contentKey="global.menu.entities.businessPartner" />
      </MenuItem>
      {/*
        <MenuItem icon="asterisk" to="/business-contact">
          <Translate contentKey="global.menu.entities.businessContact" />
        </MenuItem>
      */}
      {isAdmin && (
        <MenuItem icon="asterisk" to="/company">
          <Translate contentKey="global.menu.entities.company" />
        </MenuItem>
      )}
      <MenuItem icon="asterisk" to="/business-year">
        <Translate contentKey="global.menu.entities.businessYear" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/resource">
        <Translate contentKey="global.menu.entities.resource" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/measurement-unit">
        <Translate contentKey="global.menu.entities.measurementUnit" />
      </MenuItem>

      <hr />

      <MenuItem icon="asterisk" to="/storage">
        <Translate contentKey="global.menu.entities.storage" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/transfer-document">
        <Translate contentKey="global.menu.entities.transferDocument" />
      </MenuItem>
      {/*
        <MenuItem icon="asterisk" to="/transfer-document-item">
          <Translate contentKey="global.menu.entities.transferDocumentItem" />
        </MenuItem>
      */}
      <MenuItem icon="asterisk" to="/storage-card">
        <Translate contentKey="global.menu.entities.storageCard" />
      </MenuItem>
      {/*
        <MenuItem icon="asterisk" to="/storage-card-traffic">
          <Translate contentKey="global.menu.entities.storageCardTraffic" />
        </MenuItem>
      */}
      <MenuItem icon="asterisk" to="/census-document">
        <Translate contentKey="global.menu.entities.censusDocument" />
      </MenuItem>
      {/*
        <MenuItem icon="asterisk" to="/census-item">
          <Translate contentKey="global.menu.entities.censusItem" />
        </MenuItem>
      */}
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
