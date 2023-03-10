import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITransferDocumentItem } from 'app/shared/model/transfer-document-item.model';
import { getEntities, reset } from './transfer-document-item.reducer';

export const TransferDocumentItem = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const { status } = useParams<'status'>();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const transferDocumentItemList = useAppSelector(state => state.transferDocumentItem.entities);
  const loading = useAppSelector(state => state.transferDocumentItem.loading);
  const totalItems = useAppSelector(state => state.transferDocumentItem.totalItems);
  const links = useAppSelector(state => state.transferDocumentItem.links);
  const entity = useAppSelector(state => state.transferDocumentItem.entity);
  const updateSuccess = useAppSelector(state => state.transferDocumentItem.updateSuccess);

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
      <h2 id="transfer-document-item-heading" data-cy="TransferDocumentItemHeading">
        <Translate contentKey="donauStorageIncApp.transferDocumentItem.home.title">Transfer Document Items</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="donauStorageIncApp.transferDocumentItem.home.refreshListLabel">Refresh List</Translate>
          </Button>
          {status === 'IN_PREPARATION' && (
            <Link
              to={'/transfer-document-item/new/' + id}
              className="btn btn-primary jh-create-entity"
              id="jh-create-entity"
              data-cy="entityCreateButton"
            >
              <FontAwesomeIcon icon="plus" />
              &nbsp;
              <Translate contentKey="donauStorageIncApp.transferDocumentItem.home.createLabel">Create new Transfer Document Item</Translate>
            </Link>
          )}
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={transferDocumentItemList ? transferDocumentItemList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {transferDocumentItemList && transferDocumentItemList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="donauStorageIncApp.transferDocumentItem.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('amount')}>
                    <Translate contentKey="donauStorageIncApp.transferDocumentItem.amount">Amount</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('price')}>
                    <Translate contentKey="donauStorageIncApp.transferDocumentItem.price">Price</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('transferValue')}>
                    <Translate contentKey="donauStorageIncApp.transferDocumentItem.transferValue">Transfer Value</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="donauStorageIncApp.transferDocumentItem.transferDocument">Transfer Document</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="donauStorageIncApp.transferDocumentItem.resource">Resource</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {transferDocumentItemList.map((transferDocumentItem, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/transfer-document-item/${transferDocumentItem.id}`} color="link" size="sm">
                        {transferDocumentItem.id}
                      </Button>
                    </td>
                    <td>{transferDocumentItem.amount}</td>
                    <td>{transferDocumentItem.price}</td>
                    <td>{transferDocumentItem.transferValue}</td>
                    <td>
                      {transferDocumentItem.transferDocument ? (
                        <Link to={`/transfer-document/${transferDocumentItem.transferDocument.id}`}>
                          {transferDocumentItem.transferDocument.type + ' ' + transferDocumentItem.transferDocument.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {transferDocumentItem.resource ? (
                        <Link to={`/resource/${transferDocumentItem.resource.id}`}>{transferDocumentItem.resource.name}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/transfer-document-item/${transferDocumentItem.id}`}
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
                          to={`/transfer-document-item/${transferDocumentItem.id}/edit`}
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
                          to={`/transfer-document-item/${transferDocumentItem.id}/delete`}
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
                <Translate contentKey="donauStorageIncApp.transferDocumentItem.home.notFound">No Transfer Document Items found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default TransferDocumentItem;
