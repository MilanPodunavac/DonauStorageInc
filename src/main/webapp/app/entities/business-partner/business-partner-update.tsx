import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBusinessContact } from 'app/shared/model/business-contact.model';
import { getEntities as getBusinessContacts } from 'app/entities/business-contact/business-contact.reducer';
import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { getEntities as getLegalEntities } from 'app/entities/legal-entity/legal-entity.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { IBusinessPartner } from 'app/shared/model/business-partner.model';
import { getEntity, updateEntity, createEntity, reset } from './business-partner.reducer';

import { ICity } from 'app/shared/model/city.model';
import { getEntities as getCities } from 'app/entities/city/city.reducer';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export const BusinessPartnerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const businessContacts = useAppSelector(state => state.businessContact.entities);
  const legalEntities = useAppSelector(state => state.legalEntity.entities);
  const companies = useAppSelector(state => state.company.entities);
  const businessPartnerEntity = useAppSelector(state => state.businessPartner.entity);
  const loading = useAppSelector(state => state.businessPartner.loading);
  const updating = useAppSelector(state => state.businessPartner.updating);
  const updateSuccess = useAppSelector(state => state.businessPartner.updateSuccess);

  const cities = useAppSelector(state => state.city.entities);
  const genderValues = Object.keys(Gender);

  const handleClose = () => {
    navigate('/business-partner' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBusinessContacts({}));
    dispatch(getLegalEntities({}));
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
      ...businessPartnerEntity,
      ...values,
      businessContact: isNew
        ? {
            personalInfo: {
              ...values.businessContact,
              contactInfo: {
                ...values.businessContactInfo,
              },
            },
          }
        : businessContacts.find(it => it.id.toString() === values.businessContact.toString()),

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
          ...businessPartnerEntity,
          businessContact: businessPartnerEntity?.businessContact?.id,
          legalEntityInfo: businessPartnerEntity?.legalEntityInfo?.id,
          company: businessPartnerEntity?.company?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="donauStorageIncApp.businessPartner.home.createOrEditLabel" data-cy="BusinessPartnerCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.businessPartner.home.createOrEditLabel">Create or edit a BusinessPartner</Translate>
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
                  id="business-partner-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="business-partner-company"
                name="company"
                data-cy="company"
                label={translate('donauStorageIncApp.businessPartner.company')}
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
              {!isNew && (
                <ValidatedField
                  id="business-partner-legalEntityInfo"
                  name="legalEntityInfo"
                  data-cy="legalEntityInfo"
                  label={translate('donauStorageIncApp.businessPartner.legalEntityInfo')}
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
                <FormText>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </FormText>
              )}
              {!isNew && (
                <div className="d-flex justify-content-end">
                  <Link
                    to={`/legal-entity/${businessPartnerEntity.legalEntityInfo ? businessPartnerEntity.legalEntityInfo.id : ''}/edit`}
                    className="btn btn-primary jh-create-entity"
                    id="jh-create-entity"
                    data-cy="entityCreateButton"
                  >
                    <FontAwesomeIcon icon="plus" />
                    &nbsp;
                    <Translate contentKey="donauStorageIncApp.legalEntity.home.editLabel">Edit Legal Entity</Translate>
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
              {!isNew && (
                <ValidatedField
                  id="business-partner-businessContact"
                  name="businessContact"
                  data-cy="businessContact"
                  label={translate('donauStorageIncApp.businessPartner.businessContact')}
                  type="select"
                  required
                  disabled
                >
                  <option value="" key="0" />
                  {businessContacts
                    ? businessContacts.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.personalInfo.firstName +
                            ' ' +
                            otherEntity.personalInfo.lastName +
                            ', ' +
                            otherEntity.personalInfo.gender}
                        </option>
                      ))
                    : null}
                </ValidatedField>
              )}
              {!isNew && (
                <div className="d-flex justify-content-end">
                  <Link
                    to={`/business-contact/${businessPartnerEntity.businessContact ? businessPartnerEntity.businessContact.id : ''}/edit`}
                    className="btn btn-primary jh-create-entity"
                    id="jh-create-entity"
                    data-cy="entityCreateButton"
                  >
                    <FontAwesomeIcon icon="plus" />
                    &nbsp;
                    <Translate contentKey="donauStorageIncApp.businessContact.home.editLabel">Edit Business Contact</Translate>
                  </Link>
                </div>
              )}
              {!isNew && (
                <FormText>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </FormText>
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.person.firstName')}
                  id="businessPartner-businessContact-personalInfo-firstName"
                  name="businessContact.firstName"
                  data-cy="businessContact.firstName"
                  type="text"
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.person.middleName')}
                  id="businessPartner-businessContact-personalInfo-middleName"
                  name="businessContact.middleName"
                  data-cy="businessContact.middleName"
                  type="text"
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.person.lastName')}
                  id="businessPartner-businessContact-personalInfo-lastName"
                  name="businessContact.lastName"
                  data-cy="businessContact.lastName"
                  type="text"
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.person.maidenName')}
                  id="businessPartner-businessContact-personalInfo-maidenName"
                  name="businessContact.maidenName"
                  data-cy="businessContact.maidenName"
                  type="text"
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.person.gender')}
                  id="businessPartner-businessContact-personalInfo-gender"
                  name="businessContact.gender"
                  data-cy="businessContact.gender"
                  type="select"
                >
                  {genderValues.map(gender => (
                    <option value={gender} key={gender}>
                      {translate('donauStorageIncApp.Gender.' + gender)}
                    </option>
                  ))}
                </ValidatedField>
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.contactInfo.email')}
                  id="employee-businessContact-personalInfo-contactInfo-email"
                  name="businessContactInfo.email"
                  data-cy="businessContactInfo.email"
                  type="text"
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.contactInfo.phoneNumber')}
                  id="businessPartner-businessContact-personalInfo-contactInfo-phoneNumber"
                  name="businessContactInfo.phoneNumber"
                  data-cy="businessContactInfo.phoneNumber"
                  type="text"
                />
              )}
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/business-partner" replace color="info">
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

export default BusinessPartnerUpdate;
