import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITransferDocument } from 'app/shared/model/transfer-document.model';
import { getEntities as getTransferDocuments } from 'app/entities/transfer-document/transfer-document.reducer';
import { IResource } from 'app/shared/model/resource.model';
import { getEntities as getResources } from 'app/entities/resource/resource.reducer';
import { ITransferDocumentItem } from 'app/shared/model/transfer-document-item.model';
import { getEntity, updateEntity, createEntity, reset } from './transfer-document-item.reducer';

export const TransferDocumentItemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;
  const { documentId } = useParams<'documentId'>();
  const isWithTransfer = documentId === 'undefined';

  const transferDocuments = useAppSelector(state => state.transferDocument.entities);
  const resources = useAppSelector(state => state.resource.entities);
  const transferDocumentItemEntity = useAppSelector(state => state.transferDocumentItem.entity);
  const loading = useAppSelector(state => state.transferDocumentItem.loading);
  const updating = useAppSelector(state => state.transferDocumentItem.updating);
  const updateSuccess = useAppSelector(state => state.transferDocumentItem.updateSuccess);

  const [amount, setAmount] = useState(0);
  const [price, setPrice] = useState(0);
  const [transferValue, setTransferValue] = useState(0);

  const handleClose = () => {
    navigate(-1);
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getTransferDocuments({}));
    dispatch(getResources({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  useEffect(() => {
    setTransferValue(amount * price);
  }, [amount, price]);

  const saveEntity = values => {
    const entity = {
      ...transferDocumentItemEntity,
      ...values,
      transferValue: transferValue,
      transferDocument: transferDocuments.find(it => it.id.toString() === values.transferDocument.toString()),
      resource: resources.find(it => it.id.toString() === values.resource.toString()),
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
          transferDocument: documentId,
        }
      : {
          ...transferDocumentItemEntity,
          transferDocument: transferDocumentItemEntity?.transferDocument?.id,
          resource: transferDocumentItemEntity?.resource?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="donauStorageIncApp.transferDocumentItem.home.createOrEditLabel" data-cy="TransferDocumentItemCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.transferDocumentItem.home.createOrEditLabel">
              Create or edit a TransferDocumentItem
            </Translate>
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
                  id="transfer-document-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                  disabled
                />
              ) : null}
              <ValidatedField
                id="transfer-document-item-transferDocument"
                name="transferDocument"
                data-cy="transferDocument"
                label={translate('donauStorageIncApp.transferDocumentItem.transferDocument')}
                type="select"
                required
                disabled={isNew && !isWithTransfer}
              >
                <option value="" key="0" />
                {transferDocuments
                  ? transferDocuments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.type + ' ' + otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="transfer-document-item-resource"
                name="resource"
                data-cy="resource"
                label={translate('donauStorageIncApp.transferDocumentItem.resource')}
                type="select"
                required
              >
                <option value="" key="0" />
                {resources
                  ? resources.map(otherEntity => (
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
                label={translate('donauStorageIncApp.transferDocumentItem.amount')}
                id="transfer-document-item-amount"
                name="amount"
                data-cy="amount"
                type="text"
                onChange={e => setAmount(parseFloat(e.target.value))}
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('donauStorageIncApp.transferDocumentItem.price')}
                id="transfer-document-item-price"
                name="price"
                data-cy="price"
                type="text"
                onChange={e => setPrice(parseFloat(e.target.value))}
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('donauStorageIncApp.transferDocumentItem.transferValue')}
                id="transfer-document-item-transferValue"
                name="transferValue"
                data-cy="transferValue"
                type="text"
                value={transferValue}
                disabled
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <UncontrolledTooltip target="transferValueLabel">
                <Translate contentKey="donauStorageIncApp.transferDocumentItem.help.transferValue" />
              </UncontrolledTooltip>
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

export default TransferDocumentItemUpdate;
