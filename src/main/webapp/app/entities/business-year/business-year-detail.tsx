import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './business-year.reducer';

export const BusinessYearDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const businessYearEntity = useAppSelector(state => state.businessYear.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="businessYearDetailsHeading">
          <Translate contentKey="donauStorageIncApp.businessYear.detail.title">BusinessYear</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{businessYearEntity.id}</dd>
          <dt>
            <span id="yearCode">
              <Translate contentKey="donauStorageIncApp.businessYear.yearCode">Year Code</Translate>
            </span>
          </dt>
          <dd>{businessYearEntity.yearCode}</dd>
          <dt>
            <span id="completed">
              <Translate contentKey="donauStorageIncApp.businessYear.completed">Completed</Translate>
            </span>
          </dt>
          <dd>{businessYearEntity.completed ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.businessYear.company">Company</Translate>
          </dt>
          <dd>{businessYearEntity.company ? businessYearEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/business-year" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/business-year/${businessYearEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BusinessYearDetail;
