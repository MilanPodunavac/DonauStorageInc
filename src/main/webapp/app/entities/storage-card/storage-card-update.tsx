import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip, Table } from 'reactstrap';
import { isNumber, TextFormat, Translate, translate, ValidatedField, ValidatedForm, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { mapIdList, overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBusinessYear } from 'app/shared/model/business-year.model';
import { getEntities as getBusinessYears } from 'app/entities/business-year/business-year.reducer';
import { IResource } from 'app/shared/model/resource.model';
import { getEntities as getResources } from 'app/entities/resource/resource.reducer';
import { IStorage } from 'app/shared/model/storage.model';
import { getEntities as getStorages } from 'app/entities/storage/storage.reducer';
import { IStorageCard } from 'app/shared/model/storage-card.model';
import { getEntity, updateEntity, createEntity, reset } from './storage-card.reducer';

import StorageCardTraffic from '../storage-card-traffic';

export const StorageCardUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const businessYears = useAppSelector(state => state.businessYear.entities);
  const resources = useAppSelector(state => state.resource.entities);
  const storages = useAppSelector(state => state.storage.entities);
  const storageCardEntity = useAppSelector(state => state.storageCard.entity);
  const loading = useAppSelector(state => state.storageCard.loading);
  const updating = useAppSelector(state => state.storageCard.updating);
  const updateSuccess = useAppSelector(state => state.storageCard.updateSuccess);

  const handleClose = () => {
    navigate('/storage-card' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBusinessYears({}));
    dispatch(getResources({}));
    dispatch(getStorages({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...storageCardEntity,
      ...values,
      businessYear: businessYears.find(it => it.id.toString() === values.businessYear.toString()),
      resource: resources.find(it => it.id.toString() === values.resource.toString()),
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
      ? {}
      : {
          ...storageCardEntity,
          businessYear: storageCardEntity?.businessYear?.id,
          resource: storageCardEntity?.resource?.id,
          storage: storageCardEntity?.storage?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col>
          <h2 id="donauStorageIncApp.storageCard.home.createOrEditLabel" data-cy="StorageCardCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.storageCard.home.createOrEditLabel">Create or edit a StorageCard</Translate>
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
                  id="storage-card-id"
                  label={translate('donauStorageIncApp.storageCard.id')}
                  validate={{ required: true }}
                  disabled
                />
              ) : null}
              <ValidatedField
                id="storage-card-businessYear"
                name="businessYear"
                data-cy="businessYear"
                label={translate('donauStorageIncApp.storageCard.businessYear')}
                type="select"
                required
                disabled={!isNew}
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
                id="storage-card-resource"
                name="resource"
                data-cy="resource"
                label={translate('donauStorageIncApp.storageCard.resource')}
                type="select"
                required
                disabled={!isNew}
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
                id="storage-card-storage"
                name="storage"
                data-cy="storage"
                label={translate('donauStorageIncApp.storageCard.storage')}
                type="select"
                required
                disabled={!isNew}
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
                label={translate('donauStorageIncApp.storageCard.startingAmount')}
                id="storage-card-startingAmount"
                name="startingAmount"
                data-cy="startingAmount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
                disabled={!isNew}
              />
              <UncontrolledTooltip target="startingAmountLabel">
                <Translate contentKey="donauStorageIncApp.storageCard.help.startingAmount" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('donauStorageIncApp.storageCard.receivedAmount')}
                id="storage-card-receivedAmount"
                name="receivedAmount"
                data-cy="receivedAmount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
                disabled={!isNew}
              />
              <UncontrolledTooltip target="receivedAmountLabel">
                <Translate contentKey="donauStorageIncApp.storageCard.help.receivedAmount" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('donauStorageIncApp.storageCard.dispatchedAmount')}
                id="storage-card-dispatchedAmount"
                name="dispatchedAmount"
                data-cy="dispatchedAmount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
                disabled={!isNew}
              />
              <UncontrolledTooltip target="dispatchedAmountLabel">
                <Translate contentKey="donauStorageIncApp.storageCard.help.dispatchedAmount" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('donauStorageIncApp.storageCard.totalAmount')}
                id="storage-card-totalAmount"
                name="totalAmount"
                data-cy="totalAmount"
                type="text"
                disabled={!isNew}
              />
              <UncontrolledTooltip target="totalAmountLabel">
                <Translate contentKey="donauStorageIncApp.storageCard.help.totalAmount" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('donauStorageIncApp.storageCard.startingValue')}
                id="storage-card-startingValue"
                name="startingValue"
                data-cy="startingValue"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
                disabled={!isNew}
              />
              <UncontrolledTooltip target="startingValueLabel">
                <Translate contentKey="donauStorageIncApp.storageCard.help.startingValue" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('donauStorageIncApp.storageCard.receivedValue')}
                id="storage-card-receivedValue"
                name="receivedValue"
                data-cy="receivedValue"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
                disabled={!isNew}
              />
              <UncontrolledTooltip target="receivedValueLabel">
                <Translate contentKey="donauStorageIncApp.storageCard.help.receivedValue" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('donauStorageIncApp.storageCard.dispatchedValue')}
                id="storage-card-dispatchedValue"
                name="dispatchedValue"
                data-cy="dispatchedValue"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
                disabled={!isNew}
              />
              <UncontrolledTooltip target="dispatchedValueLabel">
                <Translate contentKey="donauStorageIncApp.storageCard.help.dispatchedValue" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('donauStorageIncApp.storageCard.totalValue')}
                id="storage-card-totalValue"
                name="totalValue"
                data-cy="totalValue"
                type="text"
                disabled={!isNew}
              />
              <UncontrolledTooltip target="totalValueLabel">
                <Translate contentKey="donauStorageIncApp.storageCard.help.totalValue" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('donauStorageIncApp.storageCard.price')}
                id="storage-card-price"
                name="price"
                data-cy="price"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
                disabled={!isNew}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/storage-card" replace color="info">
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

        {!isNew && (
          <Col>
            <StorageCardTraffic />
          </Col>
        )}
      </Row>
    </div>
  );
};

export default StorageCardUpdate;
