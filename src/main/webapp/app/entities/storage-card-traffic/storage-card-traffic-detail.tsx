import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './storage-card-traffic.reducer';

export const StorageCardTrafficDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const storageCardTrafficEntity = useAppSelector(state => state.storageCardTraffic.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="storageCardTrafficDetailsHeading">
          <Translate contentKey="donauStorageIncApp.storageCardTraffic.detail.title">StorageCardTraffic</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{storageCardTrafficEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="donauStorageIncApp.storageCardTraffic.type">Type</Translate>
            </span>
          </dt>
          <dd>{storageCardTrafficEntity.type}</dd>
          <dt>
            <span id="direction">
              <Translate contentKey="donauStorageIncApp.storageCardTraffic.direction">Direction</Translate>
            </span>
          </dt>
          <dd>{storageCardTrafficEntity.direction}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="donauStorageIncApp.storageCardTraffic.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{storageCardTrafficEntity.amount}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="donauStorageIncApp.storageCardTraffic.price">Price</Translate>
            </span>
          </dt>
          <dd>{storageCardTrafficEntity.price}</dd>
          <dt>
            <span id="trafficValue">
              <Translate contentKey="donauStorageIncApp.storageCardTraffic.trafficValue">Traffic Value</Translate>
            </span>
            <UncontrolledTooltip target="trafficValue">
              <Translate contentKey="donauStorageIncApp.storageCardTraffic.help.trafficValue" />
            </UncontrolledTooltip>
          </dt>
          <dd>{storageCardTrafficEntity.trafficValue}</dd>
          <dt>
            <span id="document">
              <Translate contentKey="donauStorageIncApp.storageCardTraffic.document">Document</Translate>
            </span>
          </dt>
          <dd>{storageCardTrafficEntity.document}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="donauStorageIncApp.storageCardTraffic.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {storageCardTrafficEntity.date ? (
              <TextFormat value={storageCardTrafficEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.storageCardTraffic.storageCard">Storage Card</Translate>
          </dt>
          <dd>{storageCardTrafficEntity.storageCard ? storageCardTrafficEntity.storageCard.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/storage-card-traffic" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/storage-card-traffic/${storageCardTrafficEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StorageCardTrafficDetail;
