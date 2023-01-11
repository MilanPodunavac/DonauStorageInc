import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContactInfo } from 'app/shared/model/contact-info.model';
import { getEntities as getContactInfos } from 'app/entities/contact-info/contact-info.reducer';
import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { getEntity, updateEntity, createEntity, reset } from './legal-entity.reducer';

export const LegalEntityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contactInfos = useAppSelector(state => state.contactInfo.entities);
  const legalEntityEntity = useAppSelector(state => state.legalEntity.entity);
  const loading = useAppSelector(state => state.legalEntity.loading);
  const updating = useAppSelector(state => state.legalEntity.updating);
  const updateSuccess = useAppSelector(state => state.legalEntity.updateSuccess);

  const handleClose = () => {
    navigate('/legal-entity');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getContactInfos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...legalEntityEntity,
      ...values,
      contactInfo: contactInfos.find(it => it.id.toString() === values.contactInfo.toString()),
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
          ...legalEntityEntity,
          contactInfo: legalEntityEntity?.contactInfo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="donauStorageIncApp.legalEntity.home.createOrEditLabel" data-cy="LegalEntityCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.legalEntity.home.createOrEditLabel">Create or edit a LegalEntity</Translate>
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
                  id="legal-entity-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('donauStorageIncApp.legalEntity.name')}
                id="legal-entity-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('donauStorageIncApp.legalEntity.taxIdentificationNumber')}
                id="legal-entity-taxIdentificationNumber"
                name="taxIdentificationNumber"
                data-cy="taxIdentificationNumber"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  pattern: { value: /[0-9]{10}/, message: translate('entity.validation.pattern', { pattern: '[0-9]{10}' }) },
                }}
              />
              <UncontrolledTooltip target="taxIdentificationNumberLabel">
                <Translate contentKey="donauStorageIncApp.legalEntity.help.taxIdentificationNumber" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('donauStorageIncApp.legalEntity.identificationNumber')}
                id="legal-entity-identificationNumber"
                name="identificationNumber"
                data-cy="identificationNumber"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  pattern: { value: /[0-9]{8}/, message: translate('entity.validation.pattern', { pattern: '[0-9]{8}' }) },
                }}
              />
              <UncontrolledTooltip target="identificationNumberLabel">
                <Translate contentKey="donauStorageIncApp.legalEntity.help.identificationNumber" />
              </UncontrolledTooltip>
              <ValidatedField
                id="legal-entity-contactInfo"
                name="contactInfo"
                data-cy="contactInfo"
                label={translate('donauStorageIncApp.legalEntity.contactInfo')}
                type="select"
                required
              >
                <option value="" key="0" />
                {contactInfos
                  ? contactInfos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/legal-entity" replace color="info">
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

export default LegalEntityUpdate;
