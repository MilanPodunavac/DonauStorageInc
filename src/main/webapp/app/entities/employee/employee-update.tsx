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
import { IPerson } from 'app/shared/model/person.model';
import { getEntities as getPeople } from 'app/entities/person/person.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntity, updateEntity, createEntity, reset } from './employee.reducer';

import { ICity } from 'app/shared/model/city.model';
import { getEntities as getCities } from 'app/entities/city/city.reducer';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export const EmployeeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const addresses = useAppSelector(state => state.address.entities);
  const people = useAppSelector(state => state.person.entities);
  const companies = useAppSelector(state => state.company.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const employeeEntity = useAppSelector(state => state.employee.entity);
  const loading = useAppSelector(state => state.employee.loading);
  const updating = useAppSelector(state => state.employee.updating);
  const updateSuccess = useAppSelector(state => state.employee.updateSuccess);

  const cities = useAppSelector(state => state.city.entities);
  const genderValues = Object.keys(Gender);
  const chosenCompany = useAppSelector(state => state.locale.businessYear.company);

  const handleClose = () => {
    navigate('/employee' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAddresses({}));
    dispatch(getPeople({}));
    dispatch(getCompanies({}));
    dispatch(getUsers({}));

    dispatch(getCities({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.birthDate = convertDateTimeToServer(values.birthDate);

    console.log(values);

    const entity = {
      ...employeeEntity,
      ...values,
      address: isNew
        ? {
            ...values.address,
            city: cities.find(it => it.id.toString() === values.address.city.toString()),
          }
        : addresses.find(it => it.id.toString() === values.address.toString()),

      personalInfo: isNew
        ? {
            ...values.personalInfo,
            contactInfo: {
              ...values.contactInfo,
            },
          }
        : people.find(it => it.id.toString() === values.personalInfo.toString()),
      company: companies.find(it => it.id.toString() === values.company.toString()),
      //user: users.find(it => it.id.toString() === values.user.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          birthDate: displayDefaultDateTime(),
          employment: true,
          company: chosenCompany?.id,
        }
      : {
          ...employeeEntity,
          birthDate: convertDateTimeFromServer(employeeEntity.birthDate),
          address: employeeEntity?.address?.id,
          personalInfo: employeeEntity?.personalInfo?.id,
          company: employeeEntity?.company?.id,
          user: employeeEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="donauStorageIncApp.employee.home.createOrEditLabel" data-cy="EmployeeCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.employee.home.createOrEditLabel">Create or edit a Employee</Translate>
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
                  id="employee-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                  disabled
                />
              ) : null}
              <ValidatedField
                id="employee-company"
                name="company"
                data-cy="company"
                label={translate('donauStorageIncApp.employee.company')}
                type="select"
                required
                disabled={!isNew || chosenCompany.id != 0}
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
                  id="employee-personalInfo"
                  name="personalInfo"
                  data-cy="personalInfo"
                  label={translate('donauStorageIncApp.employee.personalInfo')}
                  type="select"
                  required
                  disabled
                >
                  <option value="" key="0" />
                  {people
                    ? people.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.firstName + ' ' + otherEntity.lastName + ', ' + otherEntity.gender}
                        </option>
                      ))
                    : null}
                </ValidatedField>
              )}
              {!isNew && (
                <div className="d-flex justify-content-end">
                  <Link
                    to={`/person/${employeeEntity.personalInfo ? employeeEntity.personalInfo.id : ''}/edit`}
                    className="btn btn-primary jh-create-entity"
                    id="jh-create-entity"
                    data-cy="entityCreateButton"
                  >
                    <FontAwesomeIcon icon="plus" />
                    &nbsp;
                    <Translate contentKey="donauStorageIncApp.person.home.editLabel">Edit Personal Info</Translate>
                  </Link>
                </div>
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.person.firstName')}
                  id="employee-personalInfo-firstName"
                  name="personalInfo.firstName"
                  data-cy="personalInfo.firstName"
                  type="text"
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.person.middleName')}
                  id="employee-personalInfo-middleName"
                  name="personalInfo.middleName"
                  data-cy="personalInfo.middleName"
                  type="text"
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.person.lastName')}
                  id="employee-personalInfo-lastName"
                  name="personalInfo.lastName"
                  data-cy="personalInfo.lastName"
                  type="text"
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.person.maidenName')}
                  id="employee-personalInfo-maidenName"
                  name="personalInfo.maidenName"
                  data-cy="personalInfo.maidenName"
                  type="text"
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.person.gender')}
                  id="employee-personalInfo-gender"
                  name="personalInfo.gender"
                  data-cy="personalInfo.gender"
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
                  id="employee-personalInfo-contactInfo-email"
                  name="contactInfo.email"
                  data-cy="contactInfo.email"
                  type="text"
                />
              )}
              {isNew && (
                <ValidatedField
                  label={translate('donauStorageIncApp.contactInfo.phoneNumber')}
                  id="employee-personalInfo-contactInfo-phoneNumber"
                  name="contactInfo.phoneNumber"
                  data-cy="contactInfo.phoneNumber"
                  type="text"
                />
              )}
              <ValidatedField
                label={translate('donauStorageIncApp.employee.uniqueIdentificationNumber')}
                id="employee-uniqueIdentificationNumber"
                name="uniqueIdentificationNumber"
                data-cy="uniqueIdentificationNumber"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  pattern: { value: /[0-9]{13}/, message: translate('entity.validation.pattern', { pattern: '[0-9]{13}' }) },
                }}
              />
              <UncontrolledTooltip target="uniqueIdentificationNumberLabel">
                <Translate contentKey="donauStorageIncApp.employee.help.uniqueIdentificationNumber" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('donauStorageIncApp.employee.birthDate')}
                id="employee-birthDate"
                name="birthDate"
                data-cy="birthDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('donauStorageIncApp.employee.disability')}
                id="employee-disability"
                name="disability"
                data-cy="disability"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('donauStorageIncApp.employee.employment')}
                id="employee-employment"
                name="employment"
                data-cy="employment"
                check
                type="checkbox"
                disabled={isNew}
              />
              {!isNew && (
                <ValidatedField
                  id="employee-address"
                  name="address"
                  data-cy="address"
                  label={translate('donauStorageIncApp.employee.address')}
                  type="select"
                  required
                  disabled
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
                    to={`/address/${employeeEntity.address ? employeeEntity.address.id : ''}/edit`}
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
              {!isNew && (
                <ValidatedField
                  id="employee-user"
                  name="user"
                  data-cy="user"
                  label={translate('userManagement.login')}
                  type="select"
                  required
                  disabled
                >
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                    : null}
                </ValidatedField>
              )}
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee" replace color="info">
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

export default EmployeeUpdate;
