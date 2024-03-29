import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStorageCard } from 'app/shared/model/storage-card.model';
import { generateAnalytics, getEntities } from './storage-card.reducer';

export const StorageCard = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const storageCardList = useAppSelector(state => state.storageCard.entities);
  const loading = useAppSelector(state => state.storageCard.loading);
  const totalItems = useAppSelector(state => state.storageCard.totalItems);

  const chosenBusinessYear = useAppSelector(state => state.locale.businessYear);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,

        query: chosenBusinessYear.id,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const genAnalytics = entity => dispatch(generateAnalytics(entity));

  return (
    <div>
      <h2 id="storage-card-heading" data-cy="StorageCardHeading">
        <Translate contentKey="donauStorageIncApp.storageCard.home.title">Storage Cards</Translate>
        {chosenBusinessYear.id != 0 && (
          <div className="d-flex justify-content-end">
            <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
              <FontAwesomeIcon icon="sync" spin={loading} />{' '}
              <Translate contentKey="donauStorageIncApp.storageCard.home.refreshListLabel">Refresh List</Translate>
            </Button>
            <Link to="/storage-card/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
              <FontAwesomeIcon icon="plus" />
              &nbsp;
              <Translate contentKey="donauStorageIncApp.storageCard.home.createLabel">Create new Storage Card</Translate>
            </Link>
          </div>
        )}
      </h2>
      <div className="table-responsive">
        {storageCardList && storageCardList.length > 0 && chosenBusinessYear.id ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="donauStorageIncApp.storageCard.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('totalAmount')}>
                  <Translate contentKey="donauStorageIncApp.storageCard.totalAmount">Total Amount</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('totalValue')}>
                  <Translate contentKey="donauStorageIncApp.storageCard.totalValue">Total Value</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('price')}>
                  <Translate contentKey="donauStorageIncApp.storageCard.price">Price</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.storageCard.businessYear">Business Year</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.storageCard.resource">Resource</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.storageCard.storage">Storage</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {storageCardList.map((storageCard, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/storage-card/${storageCard.id}`} color="link" size="sm">
                      {storageCard.id}
                    </Button>
                  </td>
                  <td>{storageCard.totalAmount}</td>
                  <td>{storageCard.totalValue}</td>
                  <td>{storageCard.price}</td>
                  <td>
                    {storageCard.businessYear ? (
                      <Link to={`/business-year/${storageCard.businessYear.id}`}>{storageCard.businessYear.yearCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {storageCard.resource ? <Link to={`/resource/${storageCard.resource.id}`}>{storageCard.resource.name}</Link> : ''}
                  </td>
                  <td>{storageCard.storage ? <Link to={`/storage/${storageCard.storage.id}`}>{storageCard.storage.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/storage-card/${storageCard.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/storage-card/${storageCard.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/storage-card/${storageCard.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                      <Button onClick={() => genAnalytics(storageCard)} color="primary" size="sm" data-cy="entityAnalyticsButton">
                        <FontAwesomeIcon icon="pencil" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.analytics">Analytics</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : chosenBusinessYear.id != 0 ? (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="donauStorageIncApp.storageCard.home.notFound">No Storage Cards found</Translate>
            </div>
          )
        ) : (
          <div className="alert alert-warning">
            <Translate contentKey="donauStorageIncApp.storageCard.home.noBusinessYearChosen">No Business Year Chosen</Translate>
          </div>
        )}
      </div>
      {totalItems ? (
        <div className={storageCardList && storageCardList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default StorageCard;
