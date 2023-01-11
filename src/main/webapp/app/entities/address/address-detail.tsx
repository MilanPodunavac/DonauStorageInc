import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './address.reducer';

export const AddressDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">
          <Translate contentKey="donauStorageIncApp.address.detail.title">Address</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="streetName">
              <Translate contentKey="donauStorageIncApp.address.streetName">Street Name</Translate>
            </span>
          </dt>
          <dd>{addressEntity.streetName}</dd>
          <dt>
            <span id="streetCode">
              <Translate contentKey="donauStorageIncApp.address.streetCode">Street Code</Translate>
            </span>
          </dt>
          <dd>{addressEntity.streetCode}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="donauStorageIncApp.address.postalCode">Postal Code</Translate>
            </span>
            <UncontrolledTooltip target="postalCode">
              <Translate contentKey="donauStorageIncApp.address.help.postalCode" />
            </UncontrolledTooltip>
          </dt>
          <dd>{addressEntity.postalCode}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.address.city">City</Translate>
          </dt>
          <dd>{addressEntity.city ? addressEntity.city.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;
