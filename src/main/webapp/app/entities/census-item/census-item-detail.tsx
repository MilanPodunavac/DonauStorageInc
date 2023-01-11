import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './census-item.reducer';

export const CensusItemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const censusItemEntity = useAppSelector(state => state.censusItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="censusItemDetailsHeading">
          <Translate contentKey="donauStorageIncApp.censusItem.detail.title">CensusItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{censusItemEntity.id}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="donauStorageIncApp.censusItem.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{censusItemEntity.amount}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.censusItem.censusDocument">Census Document</Translate>
          </dt>
          <dd>{censusItemEntity.censusDocument ? censusItemEntity.censusDocument.id : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.censusItem.resource">Resource</Translate>
          </dt>
          <dd>{censusItemEntity.resource ? censusItemEntity.resource.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/census-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/census-item/${censusItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CensusItemDetail;
