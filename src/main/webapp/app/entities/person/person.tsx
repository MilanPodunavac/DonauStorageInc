import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPerson } from 'app/shared/model/person.model';
import { getEntities } from './person.reducer';

export const Person = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const personList = useAppSelector(state => state.person.entities);
  const loading = useAppSelector(state => state.person.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="person-heading" data-cy="PersonHeading">
        <Translate contentKey="donauStorageIncApp.person.home.title">People</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="donauStorageIncApp.person.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/person/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="donauStorageIncApp.person.home.createLabel">Create new Person</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {personList && personList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="donauStorageIncApp.person.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.person.firstName">First Name</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.person.middleName">Middle Name</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.person.lastName">Last Name</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.person.maidenName">Maiden Name</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.person.gender">Gender</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.person.contactInfo">Contact Info</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {personList.map((person, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/person/${person.id}`} color="link" size="sm">
                      {person.id}
                    </Button>
                  </td>
                  <td>{person.firstName}</td>
                  <td>{person.middleName}</td>
                  <td>{person.lastName}</td>
                  <td>{person.maidenName}</td>
                  <td>
                    <Translate contentKey={`donauStorageIncApp.Gender.${person.gender}`} />
                  </td>
                  <td>{person.contactInfo ? <Link to={`/contact-info/${person.contactInfo.id}`}>{person.contactInfo.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/person/${person.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/person/${person.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/person/${person.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="donauStorageIncApp.person.home.notFound">No People found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Person;
