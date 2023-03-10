import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBusinessPartner } from 'app/shared/model/business-partner.model';
import { getEntities } from './business-partner.reducer';

export const BusinessPartner = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const businessPartnerList = useAppSelector(state => state.businessPartner.entities);
  const loading = useAppSelector(state => state.businessPartner.loading);
  const totalItems = useAppSelector(state => state.businessPartner.totalItems);

  const chosenCompany = useAppSelector(state => state.locale.businessYear.company);

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
      <h2 id="business-partner-heading" data-cy="BusinessPartnerHeading">
        <Translate contentKey="donauStorageIncApp.businessPartner.home.title">Business Partners</Translate>
        {chosenCompany.id != 0 && (
          <div className="d-flex justify-content-end">
            <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
              <FontAwesomeIcon icon="sync" spin={loading} />{' '}
              <Translate contentKey="donauStorageIncApp.businessPartner.home.refreshListLabel">Refresh List</Translate>
            </Button>
            <Link
              to="/business-partner/new"
              className="btn btn-primary jh-create-entity"
              id="jh-create-entity"
              data-cy="entityCreateButton"
            >
              <FontAwesomeIcon icon="plus" />
              &nbsp;
              <Translate contentKey="donauStorageIncApp.businessPartner.home.createLabel">Create new Business Partner</Translate>
            </Link>
          </div>
        )}
      </h2>
      <div className="table-responsive">
        {businessPartnerList && businessPartnerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="donauStorageIncApp.businessPartner.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.legalEntity.name">Name</Translate>
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.businessPartner.businessContact">Business Contact</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.legalEntity.taxIdentificationNumber">Tax Identification Number</Translate>{' '}
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.legalEntity.identificationNumber">Identification Number</Translate>{' '}
                </th>
              </tr>
            </thead>
            <tbody>
              {businessPartnerList.map((businessPartner, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/business-partner/${businessPartner.id}`} color="link" size="sm">
                      {businessPartner.id}
                    </Button>
                  </td>
                  <td>
                    {businessPartner.legalEntityInfo ? (
                      <Link to={`/legal-entity/${businessPartner.legalEntityInfo.id}`}>{businessPartner.legalEntityInfo.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {businessPartner.businessContact ? (
                      <Link to={`/business-contact/${businessPartner.businessContact.id}`}>
                        {businessPartner.businessContact.personalInfo.firstName +
                          ' ' +
                          businessPartner.businessContact.personalInfo.lastName}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{businessPartner.legalEntityInfo.taxIdentificationNumber}</td>
                  <td>{businessPartner.legalEntityInfo.identificationNumber}</td>
                  <td>
                    {businessPartner.company ? (
                      <Link to={`/company/${businessPartner.company.id}`}>{businessPartner.company.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/business-partner/${businessPartner.id}`}
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
                        to={`/business-partner/${businessPartner.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/business-partner/${businessPartner.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
        ) : chosenCompany.id != 0 ? (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="donauStorageIncApp.businessPartner.home.notFound">No Business Partners found</Translate>
            </div>
          )
        ) : (
          <div className="alert alert-warning">
            <Translate contentKey="donauStorageIncApp.businessPartner.home.noBusinessYearChosen">No Business Year Chosen</Translate>
          </div>
        )}
      </div>
      {totalItems ? (
        <div className={businessPartnerList && businessPartnerList.length > 0 ? '' : 'd-none'}>
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

export default BusinessPartner;
