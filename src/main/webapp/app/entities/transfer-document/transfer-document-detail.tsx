import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './transfer-document.reducer';

export const TransferDocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const transferDocumentEntity = useAppSelector(state => state.transferDocument.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="transferDocumentDetailsHeading">
          <Translate contentKey="donauStorageIncApp.transferDocument.detail.title">TransferDocument</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{transferDocumentEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="donauStorageIncApp.transferDocument.type">Type</Translate>
            </span>
          </dt>
          <dd>{transferDocumentEntity.type}</dd>
          <dt>
            <span id="transferDate">
              <Translate contentKey="donauStorageIncApp.transferDocument.transferDate">Transfer Date</Translate>
            </span>
          </dt>
          <dd>
            {transferDocumentEntity.transferDate ? (
              <TextFormat value={transferDocumentEntity.transferDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="donauStorageIncApp.transferDocument.status">Status</Translate>
            </span>
          </dt>
          <dd>{transferDocumentEntity.status}</dd>
          <dt>
            <span id="accountingDate">
              <Translate contentKey="donauStorageIncApp.transferDocument.accountingDate">Accounting Date</Translate>
            </span>
            <UncontrolledTooltip target="accountingDate">
              <Translate contentKey="donauStorageIncApp.transferDocument.help.accountingDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {transferDocumentEntity.accountingDate ? (
              <TextFormat value={transferDocumentEntity.accountingDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="reversalDate">
              <Translate contentKey="donauStorageIncApp.transferDocument.reversalDate">Reversal Date</Translate>
            </span>
            <UncontrolledTooltip target="reversalDate">
              <Translate contentKey="donauStorageIncApp.transferDocument.help.reversalDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {transferDocumentEntity.reversalDate ? (
              <TextFormat value={transferDocumentEntity.reversalDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.transferDocument.businessYear">Business Year</Translate>
          </dt>
          <dd>{transferDocumentEntity.businessYear ? transferDocumentEntity.businessYear.yearCode : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.transferDocument.receivingStorage">Receiving Storage</Translate>
          </dt>
          <dd>{transferDocumentEntity.receivingStorage ? transferDocumentEntity.receivingStorage.name : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.transferDocument.dispatchingStorage">Dispatching Storage</Translate>
          </dt>
          <dd>{transferDocumentEntity.dispatchingStorage ? transferDocumentEntity.dispatchingStorage.name : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.transferDocument.businessPartner">Business Partner</Translate>
          </dt>
          <dd>{transferDocumentEntity.businessPartner ? transferDocumentEntity.businessPartner.legalEntityInfo.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/transfer-document" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/transfer-document/${transferDocumentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TransferDocumentDetail;
