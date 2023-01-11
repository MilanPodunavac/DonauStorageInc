import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContactInfo } from 'app/shared/model/contact-info.model';
import { getEntities } from './contact-info.reducer';

export const ContactInfo = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const contactInfoList = useAppSelector(state => state.contactInfo.entities);
  const loading = useAppSelector(state => state.contactInfo.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="contact-info-heading" data-cy="ContactInfoHeading">
        <Translate contentKey="donauStorageIncApp.contactInfo.home.title">Contact Infos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="donauStorageIncApp.contactInfo.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/contact-info/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="donauStorageIncApp.contactInfo.home.createLabel">Create new Contact Info</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {contactInfoList && contactInfoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="donauStorageIncApp.contactInfo.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.contactInfo.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.contactInfo.phoneNumber">Phone Number</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {contactInfoList.map((contactInfo, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/contact-info/${contactInfo.id}`} color="link" size="sm">
                      {contactInfo.id}
                    </Button>
                  </td>
                  <td>{contactInfo.email}</td>
                  <td>{contactInfo.phoneNumber}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/contact-info/${contactInfo.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/contact-info/${contactInfo.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/contact-info/${contactInfo.id}/delete`}
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
              <Translate contentKey="donauStorageIncApp.contactInfo.home.notFound">No Contact Infos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ContactInfo;
