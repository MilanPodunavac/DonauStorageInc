import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMeasurementUnit } from 'app/shared/model/measurement-unit.model';
import { getEntities as getMeasurementUnits } from 'app/entities/measurement-unit/measurement-unit.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { IResource } from 'app/shared/model/resource.model';
import { ResourceType } from 'app/shared/model/enumerations/resource-type.model';
import { getEntity, updateEntity, createEntity, reset } from './resource.reducer';

export const ResourceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const measurementUnits = useAppSelector(state => state.measurementUnit.entities);
  const companies = useAppSelector(state => state.company.entities);
  const resourceEntity = useAppSelector(state => state.resource.entity);
  const loading = useAppSelector(state => state.resource.loading);
  const updating = useAppSelector(state => state.resource.updating);
  const updateSuccess = useAppSelector(state => state.resource.updateSuccess);
  const resourceTypeValues = Object.keys(ResourceType);

  const handleClose = () => {
    navigate('/resource' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMeasurementUnits({}));
    dispatch(getCompanies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...resourceEntity,
      ...values,
      unit: measurementUnits.find(it => it.id.toString() === values.unit.toString()),
      company: companies.find(it => it.id.toString() === values.company.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          type: 'MATERIAL',
          ...resourceEntity,
          unit: resourceEntity?.unit?.id,
          company: resourceEntity?.company?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="donauStorageIncApp.resource.home.createOrEditLabel" data-cy="ResourceCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.resource.home.createOrEditLabel">Create or edit a Resource</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="resource-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('donauStorageIncApp.resource.name')}
                id="resource-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('donauStorageIncApp.resource.type')}
                id="resource-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {resourceTypeValues.map(resourceType => (
                  <option value={resourceType} key={resourceType}>
                    {translate('donauStorageIncApp.ResourceType.' + resourceType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="resource-unit"
                name="unit"
                data-cy="unit"
                label={translate('donauStorageIncApp.resource.unit')}
                type="select"
                required
              >
                <option value="" key="0" />
                {measurementUnits
                  ? measurementUnits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name + ' (' + otherEntity.abbreviation + ')'}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <div className="d-flex justify-content-end">
                <Link
                  to="/measurement-unit/new"
                  className="btn btn-primary jh-create-entity"
                  id="jh-create-entity"
                  data-cy="entityCreateButton"
                >
                  <FontAwesomeIcon icon="plus" />
                  &nbsp;
                  <Translate contentKey="donauStorageIncApp.legalEntity.home.createLabel">Create new Measurement Unit</Translate>
                </Link>
              </div>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="resource-company"
                name="company"
                data-cy="company"
                label={translate('donauStorageIncApp.resource.company')}
                type="select"
                required
              >
                <option value="" key="0" />
                {companies
                  ? companies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.legalEntityInfo.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/resource" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ResourceUpdate;
