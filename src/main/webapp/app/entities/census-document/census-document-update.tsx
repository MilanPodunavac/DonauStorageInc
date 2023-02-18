import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBusinessYear } from 'app/shared/model/business-year.model';
import { getEntities as getBusinessYears } from 'app/entities/business-year/business-year.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IStorage } from 'app/shared/model/storage.model';
import { getEntities as getStorages } from 'app/entities/storage/storage.reducer';
import { ICensusDocument } from 'app/shared/model/census-document.model';
import { CensusDocumentStatus } from 'app/shared/model/enumerations/census-document-status.model';
import { getEntity, updateEntity, createEntity, reset, account } from './census-document.reducer';
import CensusItem from '../census-item';

export const CensusDocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const businessYears = useAppSelector(state => state.businessYear.entities);
  const employees = useAppSelector(state => state.employee.entities);
  const storages = useAppSelector(state => state.storage.entities);
  const censusDocumentEntity = useAppSelector(state => state.censusDocument.entity);
  const loading = useAppSelector(state => state.censusDocument.loading);
  const updating = useAppSelector(state => state.censusDocument.updating);
  const updateSuccess = useAppSelector(state => state.censusDocument.updateSuccess);
  const censusDocumentStatusValues = Object.keys(CensusDocumentStatus);

  const censusDocumentStatus = censusDocumentEntity.status;

  const handleClose = () => {
    navigate('/census-document' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBusinessYears({}));
    dispatch(getEmployees({}));
    dispatch(getStorages({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...censusDocumentEntity,
      ...values,
      businessYear: businessYears.find(it => it.id.toString() === values.businessYear.toString()),
      president: employees.find(it => it.id.toString() === values.president.toString()),
      deputy: employees.find(it => it.id.toString() === values.deputy.toString()),
      censusTaker: employees.find(it => it.id.toString() === values.censusTaker.toString()),
      storage: storages.find(it => it.id.toString() === values.storage.toString()),
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
          status: 'INCOMPLETE',
        }
      : {
          status: 'INCOMPLETE',
          ...censusDocumentEntity,
          businessYear: censusDocumentEntity?.businessYear?.id,
          president: censusDocumentEntity?.president?.id,
          deputy: censusDocumentEntity?.deputy?.id,
          censusTaker: censusDocumentEntity?.censusTaker?.id,
          storage: censusDocumentEntity?.storage?.id,
        };

  const accountCensus = () => {
    dispatch(account(id));
    handleClose();
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="donauStorageIncApp.censusDocument.home.createOrEditLabel" data-cy="CensusDocumentCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.censusDocument.home.createOrEditLabel">Create or edit a CensusDocument</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="5">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="census-document-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                  disabled
                />
              ) : null}
              <ValidatedField
                id="census-document-storage"
                name="storage"
                data-cy="storage"
                label={translate('donauStorageIncApp.censusDocument.storage')}
                type="select"
                required
                disabled={!isNew && censusDocumentStatus != 'INCOMPLETE'}
              >
                <option value="" key="0" />
                {storages
                  ? storages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="census-document-businessYear"
                name="businessYear"
                data-cy="businessYear"
                label={translate('donauStorageIncApp.censusDocument.businessYear')}
                type="select"
                required
                disabled={!isNew && censusDocumentStatus != 'INCOMPLETE'}
              >
                <option value="" key="0" />
                {businessYears
                  ? businessYears.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.yearCode}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                label={translate('donauStorageIncApp.censusDocument.censusDate')}
                id="census-document-censusDate"
                name="censusDate"
                data-cy="censusDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
                disabled={!isNew && censusDocumentStatus != 'INCOMPLETE'}
              />
              <UncontrolledTooltip target="censusDateLabel">
                <Translate contentKey="donauStorageIncApp.censusDocument.help.censusDate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('donauStorageIncApp.censusDocument.leveling')}
                id="census-document-leveling"
                name="leveling"
                data-cy="leveling"
                check
                type="checkbox"
                disabled={!isNew && censusDocumentStatus != 'INCOMPLETE'}
              />
              <ValidatedField
                id="census-document-president"
                name="president"
                data-cy="president"
                label={translate('donauStorageIncApp.censusDocument.president')}
                type="select"
                required
                disabled={!isNew && censusDocumentStatus != 'INCOMPLETE'}
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.personalInfo.firstName + ' ' + otherEntity.personalInfo.lastName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="census-document-deputy"
                name="deputy"
                data-cy="deputy"
                label={translate('donauStorageIncApp.censusDocument.deputy')}
                type="select"
                required
                disabled={!isNew && censusDocumentStatus != 'INCOMPLETE'}
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.personalInfo.firstName + ' ' + otherEntity.personalInfo.lastName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="census-document-censusTaker"
                name="censusTaker"
                data-cy="censusTaker"
                label={translate('donauStorageIncApp.censusDocument.censusTaker')}
                type="select"
                required
                disabled={!isNew && censusDocumentStatus != 'INCOMPLETE'}
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.personalInfo.firstName + ' ' + otherEntity.personalInfo.lastName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                label={translate('donauStorageIncApp.censusDocument.status')}
                id="census-document-status"
                name="status"
                data-cy="status"
                type="select"
                disabled
              >
                {censusDocumentStatusValues.map(censusDocumentStatus => (
                  <option value={censusDocumentStatus} key={censusDocumentStatus}>
                    {translate('donauStorageIncApp.CensusDocumentStatus.' + censusDocumentStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('donauStorageIncApp.censusDocument.accountingDate')}
                id="census-document-accountingDate"
                name="accountingDate"
                data-cy="accountingDate"
                type="date"
                disabled
              />
              <UncontrolledTooltip target="accountingDateLabel">
                <Translate contentKey="donauStorageIncApp.censusDocument.help.accountingDate" />
              </UncontrolledTooltip>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/census-document" replace color="info">
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
              <Button id="account-census" data-cy="censusDocumentAccountButton" onClick={accountCensus} color="primary" disabled={updating}>
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.censusDocument.account">Account</Translate>
                </span>
              </Button>
            </ValidatedForm>
          )}
        </Col>

        <Col>
          <CensusItem />
        </Col>
      </Row>
    </div>
  );
};

export default CensusDocumentUpdate;
