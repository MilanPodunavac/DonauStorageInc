import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './legal-entity.reducer';

export const LegalEntityDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const legalEntityEntity = useAppSelector(state => state.legalEntity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="legalEntityDetailsHeading">
          <Translate contentKey="donauStorageIncApp.legalEntity.detail.title">LegalEntity</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{legalEntityEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="donauStorageIncApp.legalEntity.name">Name</Translate>
            </span>
          </dt>
          <dd>{legalEntityEntity.name}</dd>
          <dt>
            <span id="taxIdentificationNumber">
              <Translate contentKey="donauStorageIncApp.legalEntity.taxIdentificationNumber">Tax Identification Number</Translate>
            </span>
            <UncontrolledTooltip target="taxIdentificationNumber">
              <Translate contentKey="donauStorageIncApp.legalEntity.help.taxIdentificationNumber" />
            </UncontrolledTooltip>
          </dt>
          <dd>{legalEntityEntity.taxIdentificationNumber}</dd>
          <dt>
            <span id="identificationNumber">
              <Translate contentKey="donauStorageIncApp.legalEntity.identificationNumber">Identification Number</Translate>
            </span>
            <UncontrolledTooltip target="identificationNumber">
              <Translate contentKey="donauStorageIncApp.legalEntity.help.identificationNumber" />
            </UncontrolledTooltip>
          </dt>
          <dd>{legalEntityEntity.identificationNumber}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.legalEntity.contactInfo">Contact Info</Translate>
          </dt>
          <dd>{legalEntityEntity.contactInfo ? legalEntityEntity.contactInfo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/legal-entity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/legal-entity/${legalEntityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LegalEntityDetail;
