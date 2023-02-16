import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { getEntities as getLegalEntities } from 'app/entities/legal-entity/legal-entity.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { getEntity, updateEntity, createEntity, reset } from './company.reducer';

import { ICity } from 'app/shared/model/city.model';
import { getEntities as getCities } from 'app/entities/city/city.reducer';

export const CompanyUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const legalEntities = useAppSelector(state => state.legalEntity.entities);
  const companyEntity = useAppSelector(state => state.company.entity);
  const loading = useAppSelector(state => state.company.loading);
  const updating = useAppSelector(state => state.company.updating);
  const updateSuccess = useAppSelector(state => state.company.updateSuccess);

  const cities = useAppSelector(state => state.city.entities);

  const handleClose = () => {
    navigate('/company');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getLegalEntities({}));

    dispatch(getCities({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...companyEntity,
      ...values,
      legalEntityInfo: isNew
        ? {
            ...values.legalEntity,
            address: {
              ...values.address,
              city: cities.find(it => it.id.toString() === values.address.city.toString()),
            },
            contactInfo: {
              ...values.legalContactInfo,
            },
          }
        : legalEntities.find(it => it.id.toString() === values.legalEntityInfo.toString()),
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
          ...companyEntity,
          legalEntityInfo: companyEntity?.legalEntityInfo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="donauStorageIncApp.company.home.createOrEditLabel" data-cy="CompanyCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.company.home.createOrEditLabel">Create or edit a Company</Translate>
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
                  id="company-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                  disabled
                />
              ) : null}
              {!isNew && (
                <ValidatedField
                  id="company-legalEntityInfo"
                  name="legalEntityInfo"
                  data-cy="legalEntityInfo"
                  label={translate('donauStorageIncApp.company.legalEntityInfo')}
                  type="select"
                  required
                  disabled
                >
                  <option value="" key="0" />
                  {legalEntities
                    ? legalEntities.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </ValidatedField>
              )}
              {!isNew && (
                <div className="d-flex justify-content-end">
                  <Link
                    to={`/legal-entity/${companyEntity.legalEntityInfo ? companyEntity.legalEntityInfo.id : ''}/edit`}
                    className="btn btn-primary jh-create-entity"
                    id="jh-create-entity"
                    data-cy="entityCreateButton"
                  >
                    <FontAwesomeIcon icon="plus" />
                    &nbsp;
                    <Translate contentKey="donauStorageIncApp.person.home.editLabel">Edit Legal Entity</Translate>
                  </Link>
                </div>
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.legalEntity.name')}
                  id="legal-entity-name"
                  name="legalEntity.name"
                  data-cy="legalEntity.name"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.legalEntity.taxIdentificationNumber')}
                  id="legal-entity-taxIdentificationNumber"
                  name="legalEntity.taxIdentificationNumber"
                  data-cy="legalEntitytaxIdentificationNumber"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    pattern: { value: /[0-9]{10}/, message: translate('entity.validation.pattern', { pattern: '[0-9]{10}' }) },
                  }}
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.legalEntity.identificationNumber')}
                  id="legal-entity-identificationNumber"
                  name="legalEntity.identificationNumber"
                  data-cy="legalEntity.identificationNumber"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    pattern: { value: /[0-9]{8}/, message: translate('entity.validation.pattern', { pattern: '[0-9]{8}' }) },
                  }}
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.contactInfo.email')}
                  id="legalEntity-contactInfo-email"
                  name="legalContactInfo.email"
                  data-cy="legalContactInfo.email"
                  type="text"
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.contactInfo.phoneNumber')}
                  id="legalEntity-contactInfo-phoneNumber"
                  name="legalContactInfo.phoneNumber"
                  data-cy="legalContactInfo.phoneNumber"
                  type="text"
                />
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/company" replace color="info">
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

export default CompanyUpdate;
