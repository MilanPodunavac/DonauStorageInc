import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './person.reducer';

export const PersonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const personEntity = useAppSelector(state => state.person.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="personDetailsHeading">
          <Translate contentKey="donauStorageIncApp.person.detail.title">Person</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{personEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="donauStorageIncApp.person.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{personEntity.firstName}</dd>
          <dt>
            <span id="middleName">
              <Translate contentKey="donauStorageIncApp.person.middleName">Middle Name</Translate>
            </span>
          </dt>
          <dd>{personEntity.middleName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="donauStorageIncApp.person.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{personEntity.lastName}</dd>
          <dt>
            <span id="maidenName">
              <Translate contentKey="donauStorageIncApp.person.maidenName">Maiden Name</Translate>
            </span>
          </dt>
          <dd>{personEntity.maidenName}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="donauStorageIncApp.person.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{personEntity.gender}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.contactInfo.email">Email</Translate>
          </dt>
          <dd>{personEntity.contactInfo ? personEntity.contactInfo.email : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.contactInfo.phoneNumber">Phone Number</Translate>
          </dt>
          <dd>{personEntity.contactInfo ? personEntity.contactInfo.phoneNumber : ''}</dd>
        </dl>
        <Button tag={Link} to="/person" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/person/${personEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonDetail;
