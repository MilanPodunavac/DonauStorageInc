import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './storage-card.reducer';

export const StorageCardDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const storageCardEntity = useAppSelector(state => state.storageCard.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="storageCardDetailsHeading">
          <Translate contentKey="donauStorageIncApp.storageCard.detail.title">StorageCard</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="donauStorageIncApp.storageCard.id">Id</Translate>
            </span>
            <UncontrolledTooltip target="id">
              <Translate contentKey="donauStorageIncApp.storageCard.help.id" />
            </UncontrolledTooltip>
          </dt>
          <dd>{storageCardEntity.id}</dd>
          <dt>
            <span id="startingAmount">
              <Translate contentKey="donauStorageIncApp.storageCard.startingAmount">Starting Amount</Translate>
            </span>
            <UncontrolledTooltip target="startingAmount">
              <Translate contentKey="donauStorageIncApp.storageCard.help.startingAmount" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {storageCardEntity.startingAmount + (storageCardEntity.resource ? ' ' + storageCardEntity.resource.unit.abbreviation : '')}
          </dd>
          <dt>
            <span id="receivedAmount">
              <Translate contentKey="donauStorageIncApp.storageCard.receivedAmount">Received Amount</Translate>
            </span>
            <UncontrolledTooltip target="receivedAmount">
              <Translate contentKey="donauStorageIncApp.storageCard.help.receivedAmount" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {storageCardEntity.receivedAmount + (storageCardEntity.resource ? ' ' + storageCardEntity.resource.unit.abbreviation : '')}
          </dd>
          <dt>
            <span id="dispatchedAmount">
              <Translate contentKey="donauStorageIncApp.storageCard.dispatchedAmount">Dispatched Amount</Translate>
            </span>
            <UncontrolledTooltip target="dispatchedAmount">
              <Translate contentKey="donauStorageIncApp.storageCard.help.dispatchedAmount" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {storageCardEntity.dispatchedAmount + (storageCardEntity.resource ? ' ' + storageCardEntity.resource.unit.abbreviation : '')}
          </dd>
          <dt>
            <span id="totalAmount">
              <Translate contentKey="donauStorageIncApp.storageCard.totalAmount">Total Amount</Translate>
            </span>
            <UncontrolledTooltip target="totalAmount">
              <Translate contentKey="donauStorageIncApp.storageCard.help.totalAmount" />
            </UncontrolledTooltip>
          </dt>
          <dd>{storageCardEntity.totalAmount + (storageCardEntity.resource ? ' ' + storageCardEntity.resource.unit.abbreviation : '')}</dd>
          <dt>
            <span id="startingValue">
              <Translate contentKey="donauStorageIncApp.storageCard.startingValue">Starting Value</Translate>
            </span>
            <UncontrolledTooltip target="startingValue">
              <Translate contentKey="donauStorageIncApp.storageCard.help.startingValue" />
            </UncontrolledTooltip>
          </dt>
          <dd>{storageCardEntity.startingValue}</dd>
          <dt>
            <span id="receivedValue">
              <Translate contentKey="donauStorageIncApp.storageCard.receivedValue">Received Value</Translate>
            </span>
            <UncontrolledTooltip target="receivedValue">
              <Translate contentKey="donauStorageIncApp.storageCard.help.receivedValue" />
            </UncontrolledTooltip>
          </dt>
          <dd>{storageCardEntity.receivedValue}</dd>
          <dt>
            <span id="dispatchedValue">
              <Translate contentKey="donauStorageIncApp.storageCard.dispatchedValue">Dispatched Value</Translate>
            </span>
            <UncontrolledTooltip target="dispatchedValue">
              <Translate contentKey="donauStorageIncApp.storageCard.help.dispatchedValue" />
            </UncontrolledTooltip>
          </dt>
          <dd>{storageCardEntity.dispatchedValue}</dd>
          <dt>
            <span id="totalValue">
              <Translate contentKey="donauStorageIncApp.storageCard.totalValue">Total Value</Translate>
            </span>
            <UncontrolledTooltip target="totalValue">
              <Translate contentKey="donauStorageIncApp.storageCard.help.totalValue" />
            </UncontrolledTooltip>
          </dt>
          <dd>{storageCardEntity.totalValue}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="donauStorageIncApp.storageCard.price">Price</Translate>
            </span>
          </dt>
          <dd>{storageCardEntity.price}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.storageCard.businessYear">Business Year</Translate>
          </dt>
          <dd>{storageCardEntity.businessYear ? storageCardEntity.businessYear.yearCode : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.storageCard.resource">Resource</Translate>
          </dt>
          <dd>{storageCardEntity.resource ? storageCardEntity.resource.name : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.storageCard.storage">Storage</Translate>
          </dt>
          <dd>{storageCardEntity.storage ? storageCardEntity.storage.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/storage-card" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/storage-card/${storageCardEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StorageCardDetail;
