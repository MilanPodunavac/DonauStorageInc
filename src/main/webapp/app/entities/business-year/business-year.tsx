import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBusinessYear } from 'app/shared/model/business-year.model';
import { getEntities } from './business-year.reducer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';

export const BusinessYear = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const businessYearList = useAppSelector(state => state.businessYear.entities);
  const loading = useAppSelector(state => state.businessYear.loading);
  const totalItems = useAppSelector(state => state.businessYear.totalItems);

  const chosenCompany = useAppSelector(state => state.locale.businessYear.company);
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,

        query: chosenCompany.id,
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
      <h2 id="business-year-heading" data-cy="BusinessYearHeading">
        <Translate contentKey="donauStorageIncApp.businessYear.home.title">Business Years</Translate>
        {chosenCompany.id != 0 ||
          (isAdmin && (
            <div className="d-flex justify-content-end">
              <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
                <FontAwesomeIcon icon="sync" spin={loading} />{' '}
                <Translate contentKey="donauStorageIncApp.businessYear.home.refreshListLabel">Refresh List</Translate>
              </Button>
              <Link to="/business-year/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
                <FontAwesomeIcon icon="plus" />
                &nbsp;
                <Translate contentKey="donauStorageIncApp.businessYear.home.createLabel">Create new Business Year</Translate>
              </Link>
            </div>
          ))}
      </h2>
      <div className="table-responsive">
        {businessYearList && businessYearList.length > 0 && (chosenCompany.id != 0 || isAdmin) ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="donauStorageIncApp.businessYear.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('yearCode')}>
                  <Translate contentKey="donauStorageIncApp.businessYear.yearCode">Year Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('completed')}>
                  <Translate contentKey="donauStorageIncApp.businessYear.completed">Completed</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.businessYear.company">Company</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {businessYearList.map((businessYear, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/business-year/${businessYear.id}`} color="link" size="sm">
                      {businessYear.id}
                    </Button>
                  </td>
                  <td>{businessYear.yearCode}</td>
                  <td>{businessYear.completed ? 'true' : 'false'}</td>
                  <td>
                    {businessYear.company ? (
                      <Link to={`/company/${businessYear.company.id}`}>{businessYear.company.legalEntityInfo.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/business-year/${businessYear.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/business-year/${businessYear.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/business-year/${businessYear.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
        ) : chosenCompany.id != 0 || isAdmin ? (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="donauStorageIncApp.businessYear.home.notFound">No Business Years found</Translate>
            </div>
          )
        ) : (
          <div className="alert alert-warning">
            <Translate contentKey="donauStorageIncApp.businessYear.home.noBusinessYearChosen">No Business Year Chosen</Translate>
          </div>
        )}
      </div>
      {totalItems ? (
        <div className={businessYearList && businessYearList.length > 0 ? '' : 'd-none'}>
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

export default BusinessYear;
