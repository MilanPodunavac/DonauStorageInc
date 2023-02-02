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
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntity, updateEntity, createEntity, reset } from './employee.reducer';

export const EmployeeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const addresses = useAppSelector(state => state.address.entities);
  const people = useAppSelector(state => state.person.entities);
  const companies = useAppSelector(state => state.company.entities);
  const employeeEntity = useAppSelector(state => state.employee.entity);
  const loading = useAppSelector(state => state.employee.loading);
  const updating = useAppSelector(state => state.employee.updating);
  const updateSuccess = useAppSelector(state => state.employee.updateSuccess);

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
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.birthDate = convertDateTimeToServer(values.birthDate);

    const entity = {
      ...employeeEntity,
      ...values,
      address: addresses.find(it => it.id.toString() === values.address.toString()),
      personalInfo: people.find(it => it.id.toString() === values.personalInfo.toString()),
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
      ? {
          birthDate: displayDefaultDateTime(),
        }
      : {
          ...employeeEntity,
          birthDate: convertDateTimeFromServer(employeeEntity.birthDate),
          address: employeeEntity?.address?.id,
          personalInfo: employeeEntity?.personalInfo?.id,
          company: employeeEntity?.company?.id,
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
                />
              ) : null}
              <ValidatedField
                id="employee-personalInfo"
                name="personalInfo"
                data-cy="personalInfo"
                label={translate('donauStorageIncApp.employee.personalInfo')}
                type="select"
                required
                disabled={!isNew}
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <div className="d-flex justify-content-end">
                {isNew ? (
                  <Link to="/person/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
                    <FontAwesomeIcon icon="plus" />
                    &nbsp;
                    <Translate contentKey="donauStorageIncApp.person.home.createLabel">Create new Personal Info</Translate>
                  </Link>
                ) : (
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
                )}
              </div>
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
              />
              <ValidatedField
                id="employee-address"
                name="address"
                data-cy="address"
                label={translate('donauStorageIncApp.employee.address')}
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
              <div className="d-flex justify-content-end">
                {isNew ? (
                  <Link to="/address/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
                    <FontAwesomeIcon icon="plus" />
                    &nbsp;
                    <Translate contentKey="donauStorageIncApp.address.home.createLabel">Create new Address</Translate>
                  </Link>
                ) : (
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
                )}
              </div>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="employee-company"
                name="company"
                data-cy="company"
                label={translate('donauStorageIncApp.employee.company')}
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
