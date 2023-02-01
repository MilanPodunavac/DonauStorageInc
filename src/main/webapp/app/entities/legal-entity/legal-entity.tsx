import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { getEntities } from './legal-entity.reducer';

export const LegalEntity = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const legalEntityList = useAppSelector(state => state.legalEntity.entities);
  const loading = useAppSelector(state => state.legalEntity.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="legal-entity-heading" data-cy="LegalEntityHeading">
        <Translate contentKey="donauStorageIncApp.legalEntity.home.title">Legal Entities</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="donauStorageIncApp.legalEntity.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/legal-entity/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="donauStorageIncApp.legalEntity.home.createLabel">Create new Legal Entity</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {legalEntityList && legalEntityList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="donauStorageIncApp.legalEntity.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.legalEntity.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.legalEntity.taxIdentificationNumber">Tax Identification Number</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.legalEntity.identificationNumber">Identification Number</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.contactInfo.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.contactInfo.phoneNumber">Phone Number</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.legalEntity.address">Address</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {legalEntityList.map((legalEntity, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/legal-entity/${legalEntity.id}`} color="link" size="sm">
                      {legalEntity.id}
                    </Button>
                  </td>
                  <td>{legalEntity.name}</td>
                  <td>{legalEntity.taxIdentificationNumber}</td>
                  <td>{legalEntity.identificationNumber}</td>
                  <td>
                    {legalEntity.contactInfo ? (
                      <Link to={`/contact-info/${legalEntity.contactInfo.id}`}>{legalEntity.contactInfo.email}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {legalEntity.contactInfo ? (
                      <Link to={`/contact-info/${legalEntity.contactInfo.id}`}>{legalEntity.contactInfo.phoneNumber}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {legalEntity.address ? (
                      <Link to={`/address/${legalEntity.address.id}`}>
                        {legalEntity.address
                          ? legalEntity.address.streetName +
                            ' ' +
                            legalEntity.address.streetCode +
                            ', ' +
                            legalEntity.address.city.name +
                            ', ' +
                            legalEntity.address.postalCode
                          : ''}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/legal-entity/${legalEntity.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/legal-entity/${legalEntity.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/legal-entity/${legalEntity.id}/delete`}
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
              <Translate contentKey="donauStorageIncApp.legalEntity.home.notFound">No Legal Entities found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default LegalEntity;
