import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICompany } from 'app/shared/model/company.model';
import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { IBusinessYear } from 'app/shared/model/business-year.model';
import { getEntity, updateEntity, createEntity, reset, complete } from './business-year.reducer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';

export const BusinessYearUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const companies = useAppSelector(state => state.company.entities);
  const businessYearEntity = useAppSelector(state => state.businessYear.entity);
  const loading = useAppSelector(state => state.businessYear.loading);
  const updating = useAppSelector(state => state.businessYear.updating);
  const updateSuccess = useAppSelector(state => state.businessYear.updateSuccess);

  const chosenCompany = useAppSelector(state => state.locale.businessYear.company);
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));

  const handleClose = () => {
    navigate('/business-year' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCompanies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...businessYearEntity,
      ...values,
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
          completed: false,
          company: chosenCompany?.id,
        }
      : {
          ...businessYearEntity,
          company: businessYearEntity?.company?.id,
        };

  const completeYear = () => {
    dispatch(complete(id));
    handleClose();
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="donauStorageIncApp.businessYear.home.createOrEditLabel" data-cy="BusinessYearCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.businessYear.home.createOrEditLabel">Create or edit a BusinessYear</Translate>
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
                  id="business-year-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                  disabled
                />
              ) : null}
              <ValidatedField
                id="business-year-company"
                name="company"
                data-cy="company"
                label={translate('donauStorageIncApp.businessYear.company')}
                type="select"
                required
                disabled={!isNew || (chosenCompany.id != 0 && !isAdmin)}
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
              <ValidatedField
                label={translate('donauStorageIncApp.businessYear.yearCode')}
                id="business-year-yearCode"
                name="yearCode"
                data-cy="yearCode"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('donauStorageIncApp.businessYear.completed')}
                id="business-year-completed"
                name="completed"
                data-cy="completed"
                check
                type="checkbox"
                disabled
              />
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/business-year" replace color="info">
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
              <Button
                id="complete-year"
                data-cy="businessYearCompleteButton"
                onClick={completeYear}
                color="primary"
                disabled={updating || isNew || businessYearEntity.completed}
              >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.businessYear.complete">Complete</Translate>
                </span>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BusinessYearUpdate;
