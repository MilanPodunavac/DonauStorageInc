import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMeasurementUnit } from 'app/shared/model/measurement-unit.model';
import { getEntities } from './measurement-unit.reducer';

export const MeasurementUnit = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const measurementUnitList = useAppSelector(state => state.measurementUnit.entities);
  const loading = useAppSelector(state => state.measurementUnit.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="measurement-unit-heading" data-cy="MeasurementUnitHeading">
        <Translate contentKey="donauStorageIncApp.measurementUnit.home.title">Measurement Units</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="donauStorageIncApp.measurementUnit.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/measurement-unit/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="donauStorageIncApp.measurementUnit.home.createLabel">Create new Measurement Unit</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {measurementUnitList && measurementUnitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="donauStorageIncApp.measurementUnit.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.measurementUnit.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="donauStorageIncApp.measurementUnit.abbreviation">Abbreviation</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {measurementUnitList.map((measurementUnit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/measurement-unit/${measurementUnit.id}`} color="link" size="sm">
                      {measurementUnit.id}
                    </Button>
                  </td>
                  <td>{measurementUnit.name}</td>
                  <td>{measurementUnit.abbreviation}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/measurement-unit/${measurementUnit.id}`}
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
                        to={`/measurement-unit/${measurementUnit.id}/edit`}
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
                        to={`/measurement-unit/${measurementUnit.id}/delete`}
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
              <Translate contentKey="donauStorageIncApp.measurementUnit.home.notFound">No Measurement Units found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MeasurementUnit;
