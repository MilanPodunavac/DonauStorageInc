import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './measurement-unit.reducer';

export const MeasurementUnitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const measurementUnitEntity = useAppSelector(state => state.measurementUnit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="measurementUnitDetailsHeading">
          <Translate contentKey="donauStorageIncApp.measurementUnit.detail.title">MeasurementUnit</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{measurementUnitEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="donauStorageIncApp.measurementUnit.name">Name</Translate>
            </span>
          </dt>
          <dd>{measurementUnitEntity.name}</dd>
          <dt>
            <span id="abbreviation">
              <Translate contentKey="donauStorageIncApp.measurementUnit.abbreviation">Abbreviation</Translate>
            </span>
          </dt>
          <dd>{measurementUnitEntity.abbreviation}</dd>
        </dl>
        <Button tag={Link} to="/measurement-unit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/measurement-unit/${measurementUnitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MeasurementUnitDetail;
