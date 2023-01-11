import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contact-info.reducer';

export const ContactInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const contactInfoEntity = useAppSelector(state => state.contactInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactInfoDetailsHeading">
          <Translate contentKey="donauStorageIncApp.contactInfo.detail.title">ContactInfo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{contactInfoEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="donauStorageIncApp.contactInfo.email">Email</Translate>
            </span>
          </dt>
          <dd>{contactInfoEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="donauStorageIncApp.contactInfo.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{contactInfoEntity.phoneNumber}</dd>
        </dl>
        <Button tag={Link} to="/contact-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contact-info/${contactInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContactInfoDetail;
