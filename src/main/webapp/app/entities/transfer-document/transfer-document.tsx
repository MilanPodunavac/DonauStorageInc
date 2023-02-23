import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITransferDocument } from 'app/shared/model/transfer-document.model';
import { getEntities } from './transfer-document.reducer';

export const TransferDocument = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const transferDocumentList = useAppSelector(state => state.transferDocument.entities);
  const loading = useAppSelector(state => state.transferDocument.loading);
  const totalItems = useAppSelector(state => state.transferDocument.totalItems);

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

  return (
    <div>
      <h2 id="transfer-document-heading" data-cy="TransferDocumentHeading">
        <Translate contentKey="donauStorageIncApp.transferDocument.home.title">Transfer Documents</Translate>
        {chosenBusinessYear.id != 0 && (
          <div className="d-flex justify-content-end">
            <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
              <FontAwesomeIcon icon="sync" spin={loading} />{' '}
              <Translate contentKey="donauStorageIncApp.transferDocument.home.refreshListLabel">Refresh List</Translate>
            </Button>
            <Link
              to="/transfer-document/new"
              className="btn btn-primary jh-create-entity"
              id="jh-create-entity"
              data-cy="entityCreateButton"
            >
              <FontAwesomeIcon icon="plus" />
              &nbsp;
              <Translate contentKey="donauStorageIncApp.transferDocument.home.createLabel">Create new Transfer Document</Translate>
            </Link>
          </div>
        )}
      </h2>
      <div className="table-responsive">
        {transferDocumentList && transferDocumentList.length > 0 && chosenBusinessYear.id != 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="donauStorageIncApp.transferDocument.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('type')}>
                  <Translate contentKey="donauStorageIncApp.transferDocument.type">Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('transferDate')}>
                  <Translate contentKey="donauStorageIncApp.transferDocument.transferDate">Transfer Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="donauStorageIncApp.transferDocument.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('accountingDate')}>
                  <Translate contentKey="donauStorageIncApp.transferDocument.accountingDate">Accounting Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('reversalDate')}>
                  <Translate contentKey="donauStorageIncApp.transferDocument.reversalDate">Reversal Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.transferDocument.businessYear">Business Year</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.transferDocument.receivingStorage">Receiving Storage</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.transferDocument.dispatchingStorage">Dispatching Storage</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.transferDocument.businessPartner">Business Partner</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {transferDocumentList.map((transferDocument, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/transfer-document/${transferDocument.id}`} color="link" size="sm">
                      {transferDocument.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`donauStorageIncApp.TransferDocumentType.${transferDocument.type}`} />
                  </td>
                  <td>
                    {transferDocument.transferDate ? (
                      <TextFormat type="date" value={transferDocument.transferDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    <Translate contentKey={`donauStorageIncApp.TransferDocumentStatus.${transferDocument.status}`} />
                  </td>
                  <td>
                    {transferDocument.accountingDate ? (
                      <TextFormat type="date" value={transferDocument.accountingDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {transferDocument.reversalDate ? (
                      <TextFormat type="date" value={transferDocument.reversalDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {transferDocument.businessYear ? (
                      <Link to={`/business-year/${transferDocument.businessYear.id}`}>{transferDocument.businessYear.yearCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {transferDocument.receivingStorage ? (
                      <Link to={`/storage/${transferDocument.receivingStorage.id}`}>{transferDocument.receivingStorage.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {transferDocument.dispatchingStorage ? (
                      <Link to={`/storage/${transferDocument.dispatchingStorage.id}`}>{transferDocument.dispatchingStorage.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {transferDocument.businessPartner ? (
                      <Link to={`/business-partner/${transferDocument.businessPartner.id}`}>
                        {transferDocument.businessPartner.legalEntityInfo.name}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/transfer-document/${transferDocument.id}`}
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
                        to={`/transfer-document/${transferDocument.id}/edit/${transferDocument.status}?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/transfer-document/${transferDocument.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
        ) : chosenBusinessYear.id != 0 ? (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="donauStorageIncApp.transferDocument.home.notFound">No Transfer Documents found</Translate>
            </div>
          )
        ) : (
          <div className="alert alert-warning">
            <Translate contentKey="donauStorageIncApp.transferDocument.home.noBusinessYearChosen">No Business Year Chosen</Translate>
          </div>
        )}
      </div>
      {totalItems ? (
        <div className={transferDocumentList && transferDocumentList.length > 0 ? '' : 'd-none'}>
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

export default TransferDocument;
