import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './business-partner.reducer';

export const BusinessPartnerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const businessPartnerEntity = useAppSelector(state => state.businessPartner.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="businessPartnerDetailsHeading">
          <Translate contentKey="donauStorageIncApp.businessPartner.detail.title">BusinessPartner</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{businessPartnerEntity.id}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.businessPartner.businessContact">Business Contact</Translate>
          </dt>
          <dd>{businessPartnerEntity.businessContact ? businessPartnerEntity.businessContact.id : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.businessPartner.legalEntityInfo">Legal Entity Info</Translate>
          </dt>
          <dd>{businessPartnerEntity.legalEntityInfo ? businessPartnerEntity.legalEntityInfo.id : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.businessPartner.company">Company</Translate>
          </dt>
          <dd>{businessPartnerEntity.company ? businessPartnerEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/business-partner" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/business-partner/${businessPartnerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BusinessPartnerDetail;
