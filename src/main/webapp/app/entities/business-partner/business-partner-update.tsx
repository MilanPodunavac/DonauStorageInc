import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
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
      businessContact: businessContacts.find(it => it.id.toString() === values.businessContact.toString()),
      legalEntityInfo: legalEntities.find(it => it.id.toString() === values.legalEntityInfo.toString()),
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
                id="business-partner-businessContact"
                name="businessContact"
                data-cy="businessContact"
                label={translate('donauStorageIncApp.businessPartner.businessContact')}
                type="select"
                required
              >
                <option value="" key="0" />
                {businessContacts
                  ? businessContacts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <div className="d-flex justify-content-end">
                {isNew ? (
                  <Link
                    to="/business-contact/new"
                    className="btn btn-primary jh-create-entity"
                    id="jh-create-entity"
                    data-cy="entityCreateButton"
                  >
                    <FontAwesomeIcon icon="plus" />
                    &nbsp;
                    <Translate contentKey="donauStorageIncApp.businessContact.home.createLabel">Create new Business Contact</Translate>
                  </Link>
                ) : null}
              </div>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="business-partner-legalEntityInfo"
                name="legalEntityInfo"
                data-cy="legalEntityInfo"
                label={translate('donauStorageIncApp.businessPartner.legalEntityInfo')}
                type="select"
                required
              >
                <option value="" key="0" />
                {legalEntities
                  ? legalEntities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <div className="d-flex justify-content-end">
                {isNew ? (
                  <Link
                    to="/legal-entity/new"
                    className="btn btn-primary jh-create-entity"
                    id="jh-create-entity"
                    data-cy="entityCreateButton"
                  >
                    <FontAwesomeIcon icon="plus" />
                    &nbsp;
                    <Translate contentKey="donauStorageIncApp.legalEntity.home.createLabel">Create new Legal Entity</Translate>
                  </Link>
                ) : (
                  <Link
                    to={`/legal-entity/${businessPartnerEntity.legalEntity ? businessPartnerEntity.legalEntity.id : ''}/edit`}
                    className="btn btn-primary jh-create-entity"
                    id="jh-create-entity"
                    data-cy="entityCreateButton"
                  >
                    <FontAwesomeIcon icon="plus" />
                    &nbsp;
                    <Translate contentKey="donauStorageIncApp.legalEntity.home.editLabel">Edit Legal Entity</Translate>
                  </Link>
                )}
              </div>
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
