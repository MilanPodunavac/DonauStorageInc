import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContactInfo } from 'app/shared/model/contact-info.model';
import { getEntity, updateEntity, createEntity, reset } from './contact-info.reducer';

export const ContactInfoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contactInfoEntity = useAppSelector(state => state.contactInfo.entity);
  const loading = useAppSelector(state => state.contactInfo.loading);
  const updating = useAppSelector(state => state.contactInfo.updating);
  const updateSuccess = useAppSelector(state => state.contactInfo.updateSuccess);

  const handleClose = () => {
    navigate(-1);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...contactInfoEntity,
      ...values,
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
          ...contactInfoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="donauStorageIncApp.contactInfo.home.createOrEditLabel" data-cy="ContactInfoCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.contactInfo.home.createOrEditLabel">Create or edit a ContactInfo</Translate>
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
                  id="contact-info-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                  disabled
                />
              ) : null}
              <ValidatedField
                label={translate('donauStorageIncApp.contactInfo.email')}
                id="contact-info-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  pattern: {
                    value: /^[^@\s]+@[^@\s]+\.[^@\s]+$/,
                    message: translate('entity.validation.pattern', { pattern: '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$' }),
                  },
                }}
              />
              <ValidatedField
                label={translate('donauStorageIncApp.contactInfo.phoneNumber')}
                id="contact-info-phoneNumber"
                name="phoneNumber"
                data-cy="phoneNumber"
                type="text"
                validate={{
                  pattern: {
                    value: /((\+[0-9]{1,3})|0)[0-9]{7,10}/,
                    message: translate('entity.validation.pattern', { pattern: '((\\+[0-9]{1,3})|0)[0-9]{7,10}' }),
                  },
                }}
              />
              <Button id="cancel-save" data-cy="entityCreateCancelButton" onClick={handleClose} replace color="info">
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

export default ContactInfoUpdate;
