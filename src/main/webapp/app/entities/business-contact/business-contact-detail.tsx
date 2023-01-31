import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './business-contact.reducer';

export const BusinessContactDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const businessContactEntity = useAppSelector(state => state.businessContact.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="businessContactDetailsHeading">
          <Translate contentKey="donauStorageIncApp.businessContact.detail.title">BusinessContact</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{businessContactEntity.id}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.person.fullName">Name</Translate>
          </dt>
          <dd>
            {businessContactEntity.personalInfo
              ? businessContactEntity.personalInfo.firstName +
                (businessContactEntity.personalInfo.middleName ? ' ' + businessContactEntity.personalInfo.middleName : '') +
                ' ' +
                businessContactEntity.personalInfo.lastName +
                (businessContactEntity.personalInfo.maidenName ? ' (' + businessContactEntity.personalInfo.maidenName + ')' : '')
              : ''}
          </dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.person.gender">Gender</Translate>
          </dt>
          <dd>{businessContactEntity.personalInfo ? businessContactEntity.personalInfo.gender : ''}</dd>
        </dl>
        <Button tag={Link} to="/business-contact" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/business-contact/${businessContactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BusinessContactDetail;
