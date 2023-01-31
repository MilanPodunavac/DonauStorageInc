import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './storage.reducer';

export const StorageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const storageEntity = useAppSelector(state => state.storage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="storageDetailsHeading">
          <Translate contentKey="donauStorageIncApp.storage.detail.title">Storage</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="donauStorageIncApp.storage.id">Id</Translate>
            </span>
            <UncontrolledTooltip target="id">
              <Translate contentKey="donauStorageIncApp.storage.help.id" />
            </UncontrolledTooltip>
          </dt>
          <dd>{storageEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="donauStorageIncApp.storage.name">Name</Translate>
            </span>
          </dt>
          <dd>{storageEntity.name}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.storage.address">Address</Translate>
          </dt>
          <dd>
            {storageEntity.address
              ? storageEntity.address.streetName +
                ' ' +
                storageEntity.address.streetCode +
                ', ' +
                storageEntity.address.city.name +
                ', ' +
                storageEntity.address.postalCode
              : ''}
          </dd>

          <dt>
            <Translate contentKey="donauStorageIncApp.storage.company">Company</Translate>
          </dt>
          <dd>{storageEntity.company ? storageEntity.company.legalEntityInfo.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/storage" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/storage/${storageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StorageDetail;
