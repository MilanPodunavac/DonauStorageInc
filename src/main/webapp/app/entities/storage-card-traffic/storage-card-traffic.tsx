import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStorageCardTraffic } from 'app/shared/model/storage-card-traffic.model';
import { getEntities, reset } from './storage-card-traffic.reducer';

export const StorageCardTraffic = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const { id } = useParams<'id'>();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const storageCardTrafficList = useAppSelector(state => state.storageCardTraffic.entities);
  const loading = useAppSelector(state => state.storageCardTraffic.loading);
  const totalItems = useAppSelector(state => state.storageCardTraffic.totalItems);
  const links = useAppSelector(state => state.storageCardTraffic.links);
  const entity = useAppSelector(state => state.storageCardTraffic.entity);
  const updateSuccess = useAppSelector(state => state.storageCardTraffic.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,

        query: id,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(
      getEntities({
        query: id,
      })
    );
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  return (
    <div>
      <h2 id="storage-card-traffic-heading" data-cy="StorageCardTrafficHeading">
        <Translate contentKey="donauStorageIncApp.storageCardTraffic.home.title">Storage Card Traffics</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="donauStorageIncApp.storageCardTraffic.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/storage-card-traffic/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="donauStorageIncApp.storageCardTraffic.home.createLabel">Create new Storage Card Traffic</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={storageCardTrafficList ? storageCardTrafficList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {storageCardTrafficList && storageCardTrafficList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="donauStorageIncApp.storageCardTraffic.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('type')}>
                    <Translate contentKey="donauStorageIncApp.storageCardTraffic.type">Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('direction')}>
                    <Translate contentKey="donauStorageIncApp.storageCardTraffic.direction">Direction</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('amount')}>
                    <Translate contentKey="donauStorageIncApp.storageCardTraffic.amount">Amount</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('price')}>
                    <Translate contentKey="donauStorageIncApp.storageCardTraffic.price">Price</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('trafficValue')}>
                    <Translate contentKey="donauStorageIncApp.storageCardTraffic.trafficValue">Traffic Value</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('document')}>
                    <Translate contentKey="donauStorageIncApp.storageCardTraffic.document">Document</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('date')}>
                    <Translate contentKey="donauStorageIncApp.storageCardTraffic.date">Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="donauStorageIncApp.storageCardTraffic.storageCard">Storage Card</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {storageCardTrafficList.map((storageCardTraffic, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/storage-card-traffic/${storageCardTraffic.id}`} color="link" size="sm">
                        {storageCardTraffic.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`donauStorageIncApp.StorageCardTrafficType.${storageCardTraffic.type}`} />
                    </td>
                    <td>
                      <Translate contentKey={`donauStorageIncApp.StorageCardTrafficDirection.${storageCardTraffic.direction}`} />
                    </td>
                    <td>{storageCardTraffic.amount}</td>
                    <td>{storageCardTraffic.price}</td>
                    <td>{storageCardTraffic.trafficValue}</td>
                    <td>{storageCardTraffic.document}</td>
                    <td>
                      {storageCardTraffic.date ? (
                        <TextFormat type="date" value={storageCardTraffic.date} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {storageCardTraffic.storageCard ? (
                        <Link to={`/storage-card/${storageCardTraffic.storageCard.id}`}>{storageCardTraffic.storageCard.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/storage-card-traffic/${storageCardTraffic.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/storage-card-traffic/${storageCardTraffic.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/storage-card-traffic/${storageCardTraffic.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="donauStorageIncApp.storageCardTraffic.home.notFound">No Storage Card Traffics found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default StorageCardTraffic;
