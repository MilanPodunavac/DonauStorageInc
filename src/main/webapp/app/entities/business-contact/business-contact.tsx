import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBusinessContact } from 'app/shared/model/business-contact.model';
import { getEntities } from './business-contact.reducer';

export const BusinessContact = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const businessContactList = useAppSelector(state => state.businessContact.entities);
  const loading = useAppSelector(state => state.businessContact.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="business-contact-heading" data-cy="BusinessContactHeading">
        <Translate contentKey="donauStorageIncApp.businessContact.home.title">Business Contacts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="donauStorageIncApp.businessContact.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/business-contact/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="donauStorageIncApp.businessContact.home.createLabel">Create new Business Contact</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {businessContactList && businessContactList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="donauStorageIncApp.businessContact.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.person.fullName">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.person.gender">Gender</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {businessContactList.map((businessContact, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/business-contact/${businessContact.id}`} color="link" size="sm">
                      {businessContact.id}
                    </Button>
                  </td>
                  <td>
                    {businessContact.personalInfo ? (
                      <Link to={`/person/${businessContact.personalInfo.id}`}>
                        {businessContact.personalInfo.firstName + ' ' + businessContact.personalInfo.lastName}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{businessContact.personalInfo.gender}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/business-contact/${businessContact.id}`}
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
                        to={`/business-contact/${businessContact.id}/edit`}
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
                        to={`/business-contact/${businessContact.id}/delete`}
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
              <Translate contentKey="donauStorageIncApp.businessContact.home.notFound">No Business Contacts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BusinessContact;
