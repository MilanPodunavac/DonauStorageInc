import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './transfer-document-item.reducer';

export const TransferDocumentItemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const transferDocumentItemEntity = useAppSelector(state => state.transferDocumentItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="transferDocumentItemDetailsHeading">
          <Translate contentKey="donauStorageIncApp.transferDocumentItem.detail.title">TransferDocumentItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{transferDocumentItemEntity.id}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="donauStorageIncApp.transferDocumentItem.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{transferDocumentItemEntity.amount}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="donauStorageIncApp.transferDocumentItem.price">Price</Translate>
            </span>
          </dt>
          <dd>{transferDocumentItemEntity.price}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="donauStorageIncApp.transferDocumentItem.value">Value</Translate>
            </span>
            <UncontrolledTooltip target="value">
              <Translate contentKey="donauStorageIncApp.transferDocumentItem.help.value" />
            </UncontrolledTooltip>
          </dt>
          <dd>{transferDocumentItemEntity.value}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.transferDocumentItem.transferDocument">Transfer Document</Translate>
          </dt>
          <dd>{transferDocumentItemEntity.transferDocument ? transferDocumentItemEntity.transferDocument.id : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.transferDocumentItem.resource">Resource</Translate>
          </dt>
          <dd>{transferDocumentItemEntity.resource ? transferDocumentItemEntity.resource.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/transfer-document-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/transfer-document-item/${transferDocumentItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TransferDocumentItemDetail;
