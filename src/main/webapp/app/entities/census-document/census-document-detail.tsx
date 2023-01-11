import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './census-document.reducer';

export const CensusDocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const censusDocumentEntity = useAppSelector(state => state.censusDocument.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="censusDocumentDetailsHeading">
          <Translate contentKey="donauStorageIncApp.censusDocument.detail.title">CensusDocument</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{censusDocumentEntity.id}</dd>
          <dt>
            <span id="censusDate">
              <Translate contentKey="donauStorageIncApp.censusDocument.censusDate">Census Date</Translate>
            </span>
            <UncontrolledTooltip target="censusDate">
              <Translate contentKey="donauStorageIncApp.censusDocument.help.censusDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {censusDocumentEntity.censusDate ? (
              <TextFormat value={censusDocumentEntity.censusDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="donauStorageIncApp.censusDocument.status">Status</Translate>
            </span>
          </dt>
          <dd>{censusDocumentEntity.status}</dd>
          <dt>
            <span id="accountingDate">
              <Translate contentKey="donauStorageIncApp.censusDocument.accountingDate">Accounting Date</Translate>
            </span>
            <UncontrolledTooltip target="accountingDate">
              <Translate contentKey="donauStorageIncApp.censusDocument.help.accountingDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {censusDocumentEntity.accountingDate ? (
              <TextFormat value={censusDocumentEntity.accountingDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="leveling">
              <Translate contentKey="donauStorageIncApp.censusDocument.leveling">Leveling</Translate>
            </span>
          </dt>
          <dd>{censusDocumentEntity.leveling ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.censusDocument.businessYear">Business Year</Translate>
          </dt>
          <dd>{censusDocumentEntity.businessYear ? censusDocumentEntity.businessYear.id : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.censusDocument.president">President</Translate>
          </dt>
          <dd>{censusDocumentEntity.president ? censusDocumentEntity.president.id : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.censusDocument.deputy">Deputy</Translate>
          </dt>
          <dd>{censusDocumentEntity.deputy ? censusDocumentEntity.deputy.id : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.censusDocument.censusTaker">Census Taker</Translate>
          </dt>
          <dd>{censusDocumentEntity.censusTaker ? censusDocumentEntity.censusTaker.id : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.censusDocument.storage">Storage</Translate>
          </dt>
          <dd>{censusDocumentEntity.storage ? censusDocumentEntity.storage.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/census-document" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/census-document/${censusDocumentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CensusDocumentDetail;
