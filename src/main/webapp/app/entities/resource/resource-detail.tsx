import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './resource.reducer';

export const ResourceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const resourceEntity = useAppSelector(state => state.resource.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="resourceDetailsHeading">
          <Translate contentKey="donauStorageIncApp.resource.detail.title">Resource</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{resourceEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="donauStorageIncApp.resource.name">Name</Translate>
            </span>
          </dt>
          <dd>{resourceEntity.name}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="donauStorageIncApp.resource.type">Type</Translate>
            </span>
          </dt>
          <dd>{resourceEntity.type}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.resource.unit">Unit</Translate>
          </dt>
          <dd>{resourceEntity.unit ? resourceEntity.unit.id : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.resource.company">Company</Translate>
          </dt>
          <dd>{resourceEntity.company ? resourceEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/resource" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/resource/${resourceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ResourceDetail;
