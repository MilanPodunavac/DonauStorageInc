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
import { IStorage } from 'app/shared/model/storage.model';
import { getEntities as getStorages } from 'app/entities/storage/storage.reducer';
import { IBusinessPartner } from 'app/shared/model/business-partner.model';
import { getEntities as getBusinessPartners } from 'app/entities/business-partner/business-partner.reducer';
import { ITransferDocument } from 'app/shared/model/transfer-document.model';
import { TransferDocumentType } from 'app/shared/model/enumerations/transfer-document-type.model';
import { TransferDocumentStatus } from 'app/shared/model/enumerations/transfer-document-status.model';
import { getEntity, updateEntity, createEntity, reset, account, reverse } from './transfer-document.reducer';

export const TransferDocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const businessYears = useAppSelector(state => state.businessYear.entities);
  const storages = useAppSelector(state => state.storage.entities);
  const businessPartners = useAppSelector(state => state.businessPartner.entities);
  const transferDocumentEntity = useAppSelector(state => state.transferDocument.entity);
  const loading = useAppSelector(state => state.transferDocument.loading);
  const updating = useAppSelector(state => state.transferDocument.updating);
  const updateSuccess = useAppSelector(state => state.transferDocument.updateSuccess);
  const transferDocumentTypeValues = Object.keys(TransferDocumentType);
  const transferDocumentStatusValues = Object.keys(TransferDocumentStatus);

  const handleClose = () => {
    navigate('/transfer-document' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBusinessYears({}));
    dispatch(getStorages({}));
    dispatch(getBusinessPartners({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...transferDocumentEntity,
      ...values,
      businessYear: businessYears.find(it => it.id.toString() === values.businessYear.toString()),
      receivingStorage: storages.find(it => it.id.toString() === values.receivingStorage.toString()),
      dispatchingStorage: storages.find(it => it.id.toString() === values.dispatchingStorage.toString()),
      businessPartner: businessPartners.find(it => it.id.toString() === values.businessPartner.toString()),
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
          status: 'IN_PREPARATION',
        }
      : {
          type: 'RECEIVING',
          status: 'IN_PREPARATION',
          ...transferDocumentEntity,
          businessYear: transferDocumentEntity?.businessYear?.id,
          receivingStorage: transferDocumentEntity?.receivingStorage?.id,
          dispatchingStorage: transferDocumentEntity?.dispatchingStorage?.id,
          businessPartner: transferDocumentEntity?.businessPartner?.id,
        };

  const accountTransfer = () => {
    dispatch(account(id));
    handleClose();
  };

  const reverseTransfer = () => {
    dispatch(reverse(id));
    handleClose();
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="donauStorageIncApp.transferDocument.home.createOrEditLabel" data-cy="TransferDocumentCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.transferDocument.home.createOrEditLabel">Create or edit a TransferDocument</Translate>
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
                  id="transfer-document-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                  disabled
                />
              ) : null}
              <ValidatedField
                label={translate('donauStorageIncApp.transferDocument.type')}
                id="transfer-document-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {transferDocumentTypeValues.map(transferDocumentType => (
                  <option value={transferDocumentType} key={transferDocumentType}>
                    {translate('donauStorageIncApp.TransferDocumentType.' + transferDocumentType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('donauStorageIncApp.transferDocument.transferDate')}
                id="transfer-document-transferDate"
                name="transferDate"
                data-cy="transferDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('donauStorageIncApp.transferDocument.status')}
                id="transfer-document-status"
                name="status"
                data-cy="status"
                type="select"
                disabled
              >
                {transferDocumentStatusValues.map(transferDocumentStatus => (
                  <option value={transferDocumentStatus} key={transferDocumentStatus}>
                    {translate('donauStorageIncApp.TransferDocumentStatus.' + transferDocumentStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('donauStorageIncApp.transferDocument.accountingDate')}
                id="transfer-document-accountingDate"
                name="accountingDate"
                data-cy="accountingDate"
                type="date"
              />
              <UncontrolledTooltip target="accountingDateLabel">
                <Translate contentKey="donauStorageIncApp.transferDocument.help.accountingDate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('donauStorageIncApp.transferDocument.reversalDate')}
                id="transfer-document-reversalDate"
                name="reversalDate"
                data-cy="reversalDate"
                type="date"
              />
              <UncontrolledTooltip target="reversalDateLabel">
                <Translate contentKey="donauStorageIncApp.transferDocument.help.reversalDate" />
              </UncontrolledTooltip>
              <ValidatedField
                id="transfer-document-businessYear"
                name="businessYear"
                data-cy="businessYear"
                label={translate('donauStorageIncApp.transferDocument.businessYear')}
                type="select"
                required
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
                id="transfer-document-receivingStorage"
                name="receivingStorage"
                data-cy="receivingStorage"
                label={translate('donauStorageIncApp.transferDocument.receivingStorage')}
                type="select"
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
              <ValidatedField
                id="transfer-document-dispatchingStorage"
                name="dispatchingStorage"
                data-cy="dispatchingStorage"
                label={translate('donauStorageIncApp.transferDocument.dispatchingStorage')}
                type="select"
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
              <ValidatedField
                id="transfer-document-businessPartner"
                name="businessPartner"
                data-cy="businessPartner"
                label={translate('donauStorageIncApp.transferDocument.businessPartner')}
                type="select"
              >
                <option value="" key="0" />
                {businessPartners
                  ? businessPartners.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.legalEntityInfo.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/transfer-document" replace color="info">
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
                id="account-transfer"
                data-cy="transferDocumentAccountButton"
                onClick={accountTransfer}
                color="primary"
                disabled={updating}
              >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.transferDocument.account">Account</Translate>
                </span>
              </Button>
              <Button
                id="reverse-transfer"
                data-cy="transferDocumentReverseButton"
                onClick={reverseTransfer}
                color="primary"
                disabled={updating}
              >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.transferDocument.reverse">Reverse</Translate>
                </span>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TransferDocumentUpdate;
