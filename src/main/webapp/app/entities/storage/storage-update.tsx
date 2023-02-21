import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { IStorage } from 'app/shared/model/storage.model';
import { getEntity, updateEntity, createEntity, reset } from './storage.reducer';

import { ICity } from 'app/shared/model/city.model';
import { getEntities as getCities } from 'app/entities/city/city.reducer';

export const StorageUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const addresses = useAppSelector(state => state.address.entities);
  const companies = useAppSelector(state => state.company.entities);
  const storageEntity = useAppSelector(state => state.storage.entity);
  const loading = useAppSelector(state => state.storage.loading);
  const updating = useAppSelector(state => state.storage.updating);
  const updateSuccess = useAppSelector(state => state.storage.updateSuccess);

  const cities = useAppSelector(state => state.city.entities);

  const handleClose = () => {
    navigate('/storage' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAddresses({}));
    dispatch(getCompanies({}));

    dispatch(getCities({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...storageEntity,
      ...values,
      address: isNew
        ? {
            ...values.address,
            city: cities.find(it => it.id.toString() === values.address.city.toString()),
          }
        : addresses.find(it => it.id.toString() === values.address.toString()),
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
          ...storageEntity,
          address: storageEntity?.address?.id,
          company: storageEntity?.company?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="donauStorageIncApp.storage.home.createOrEditLabel" data-cy="StorageCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.storage.home.createOrEditLabel">Create or edit a Storage</Translate>
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
                  id="storage-id"
                  label={translate('donauStorageIncApp.storage.id')}
                  validate={{ required: true }}
                  disabled
                />
              ) : null}
              <ValidatedField
                id="storage-company"
                name="company"
                data-cy="company"
                label={translate('donauStorageIncApp.storage.company')}
                type="select"
                required
                disabled={!isNew}
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
              <ValidatedField
                label={translate('donauStorageIncApp.storage.name')}
                id="storage-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('donauStorageIncApp.storage.code')}
                id="storage-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              {!isNew && (
                <ValidatedField
                  id="storage-address"
                  name="address"
                  data-cy="address"
                  label={translate('donauStorageIncApp.storage.address')}
                  type="select"
                  required
                  disabled={!isNew}
                >
                  <option value="" key="0" />
                  {addresses
                    ? addresses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.streetName + ' ' + otherEntity.streetCode + ', ' + otherEntity.city.name}
                        </option>
                      ))
                    : null}
                </ValidatedField>
              )}
              {!isNew && (
                <FormText>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </FormText>
              )}
              {!isNew && (
                <div className="d-flex justify-content-end">
                  <Link
                    to={`/address/${storageEntity.address ? storageEntity.address.id : ''}/edit`}
                    className="btn btn-primary jh-create-entity"
                    id="jh-create-entity"
                    data-cy="entityCreateButton"
                  >
                    <FontAwesomeIcon icon="plus" />
                    &nbsp;
                    <Translate contentKey="donauStorageIncApp.address.home.editLabel">Edit Address</Translate>
                  </Link>
                </div>
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.address.streetName')}
                  id="employee-address-streetName"
                  name="address.streetName"
                  data-cy="address.streetName"
                  type="text"
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.address.streetCode')}
                  id="employee-address-streetCode"
                  name="address.streetCode"
                  data-cy="address.streetCode"
                  type="text"
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.address.postalCode')}
                  id="employee-address-postalCode"
                  name="address.postalCode"
                  data-cy="address.postalCode"
                  type="text"
                />
              )}
              {isNew && (
                <ValidatedField
                  id="employee-address-city"
                  name="address.city"
                  data-cy="address.city"
                  label={translate('donauStorageIncApp.address.city')}
                  type="select"
                  required
                >
                  <option value="" key="0" />
                  {cities
                    ? cities.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name + ', ' + otherEntity.country.name}
                        </option>
                      ))
                    : null}
                </ValidatedField>
              )}
              {!isNew && (
                <div className="d-flex justify-content-end">
                  <Link
                    to={`/address/${storageEntity.address ? storageEntity.address.id : ''}/edit`}
                    className="btn btn-primary jh-create-entity"
                    id="jh-create-entity"
                    data-cy="entityCreateButton"
                  >
                    <FontAwesomeIcon icon="plus" />
                    &nbsp;
                    <Translate contentKey="donauStorageIncApp.address.home.editLabel">Edit Address</Translate>
                  </Link>
                </div>
              )}
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/storage" replace color="info">
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

export default StorageUpdate;
