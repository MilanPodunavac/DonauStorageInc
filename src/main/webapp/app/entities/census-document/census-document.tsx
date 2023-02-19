import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICensusDocument } from 'app/shared/model/census-document.model';
import { getEntities } from './census-document.reducer';

export const CensusDocument = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const censusDocumentList = useAppSelector(state => state.censusDocument.entities);
  const loading = useAppSelector(state => state.censusDocument.loading);
  const totalItems = useAppSelector(state => state.censusDocument.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
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
      <h2 id="census-document-heading" data-cy="CensusDocumentHeading">
        <Translate contentKey="donauStorageIncApp.censusDocument.home.title">Census Documents</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="donauStorageIncApp.censusDocument.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/census-document/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="donauStorageIncApp.censusDocument.home.createLabel">Create new Census Document</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {censusDocumentList && censusDocumentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="donauStorageIncApp.censusDocument.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('censusDate')}>
                  <Translate contentKey="donauStorageIncApp.censusDocument.censusDate">Census Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="donauStorageIncApp.censusDocument.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('accountingDate')}>
                  <Translate contentKey="donauStorageIncApp.censusDocument.accountingDate">Accounting Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('leveling')}>
                  <Translate contentKey="donauStorageIncApp.censusDocument.leveling">Leveling</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.censusDocument.businessYear">Business Year</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.censusDocument.president">President</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.censusDocument.deputy">Deputy</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.censusDocument.censusTaker">Census Taker</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.censusDocument.storage">Storage</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {censusDocumentList.map((censusDocument, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/census-document/${censusDocument.id}`} color="link" size="sm">
                      {censusDocument.id}
                    </Button>
                  </td>
                  <td>
                    {censusDocument.censusDate ? (
                      <TextFormat type="date" value={censusDocument.censusDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    <Translate contentKey={`donauStorageIncApp.CensusDocumentStatus.${censusDocument.status}`} />
                  </td>
                  <td>
                    {censusDocument.accountingDate ? (
                      <TextFormat type="date" value={censusDocument.accountingDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{censusDocument.leveling ? 'true' : 'false'}</td>
                  <td>
                    {censusDocument.businessYear ? (
                      <Link to={`/business-year/${censusDocument.businessYear.id}`}>{censusDocument.businessYear.yearCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {censusDocument.storage ? <Link to={`/storage/${censusDocument.storage.id}`}>{censusDocument.storage.name}</Link> : ''}
                  </td>
                  <td>
                    {censusDocument.president ? (
                      <Link to={`/employee/${censusDocument.president.id}`}>
                        {censusDocument.president.personalInfo.firstName + ' ' + censusDocument.president.personalInfo.lastName}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {censusDocument.deputy ? (
                      <Link to={`/employee/${censusDocument.deputy.id}`}>
                        {censusDocument.deputy.personalInfo.firstName + ' ' + censusDocument.deputy.personalInfo.lastName}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {censusDocument.censusTaker ? (
                      <Link to={`/employee/${censusDocument.censusTaker.id}`}>
                        {censusDocument.censusTaker.personalInfo.firstName + ' ' + censusDocument.censusTaker.personalInfo.lastName}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/census-document/${censusDocument.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/census-document/${censusDocument.id}/edit/${censusDocument.status}?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/census-document/${censusDocument.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="donauStorageIncApp.censusDocument.home.notFound">No Census Documents found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={censusDocumentList && censusDocumentList.length > 0 ? '' : 'd-none'}>
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

export default CensusDocument;
