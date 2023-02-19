import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStorageCard } from 'app/shared/model/storage-card.model';
import { getEntities as getStorageCards } from 'app/entities/storage-card/storage-card.reducer';
import { IStorageCardTraffic } from 'app/shared/model/storage-card-traffic.model';
import { StorageCardTrafficType } from 'app/shared/model/enumerations/storage-card-traffic-type.model';
import { StorageCardTrafficDirection } from 'app/shared/model/enumerations/storage-card-traffic-direction.model';
import { getEntity, updateEntity, createEntity, reset } from './storage-card-traffic.reducer';

export const StorageCardTrafficUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;
  const { cardId } = useParams<'cardId'>();
  const isWithCard = cardId === 'undefined';

  const storageCards = useAppSelector(state => state.storageCard.entities);
  const storageCardTrafficEntity = useAppSelector(state => state.storageCardTraffic.entity);
  const loading = useAppSelector(state => state.storageCardTraffic.loading);
  const updating = useAppSelector(state => state.storageCardTraffic.updating);
  const updateSuccess = useAppSelector(state => state.storageCardTraffic.updateSuccess);
  const storageCardTrafficTypeValues = Object.keys(StorageCardTrafficType);
  const storageCardTrafficDirectionValues = Object.keys(StorageCardTrafficDirection);

  const [amount, setAmount] = useState(0);
  const [price, setPrice] = useState(0);
  const [trafficValue, setTrafficValue] = useState(0);

  const handleClose = () => {
    navigate(-1);
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getStorageCards({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...storageCardTrafficEntity,
      ...values,
      trafficValue: trafficValue,
      storageCard: storageCards.find(it => it.id.toString() === values.storageCard.toString()),
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
          storageCard: cardId,
        }
      : {
          type: 'STARTING_BALANCE',
          direction: 'IN',
          ...storageCardTrafficEntity,
          storageCard: storageCardTrafficEntity?.storageCard?.id,
        };

  useEffect(() => {
    setTrafficValue(amount * price);
  }, [amount, price]);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="donauStorageIncApp.storageCardTraffic.home.createOrEditLabel" data-cy="StorageCardTrafficCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.storageCardTraffic.home.createOrEditLabel">
              Create or edit a StorageCardTraffic
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
                  id="storage-card-traffic-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                  disabled
                />
              ) : null}
              <ValidatedField
                id="storage-card-traffic-storageCard"
                name="storageCard"
                data-cy="storageCard"
                label={translate('donauStorageIncApp.storageCardTraffic.storageCard')}
                type="select"
                required
                disabled={isNew && !isWithCard}
              >
                <option value="" key="0" />
                {storageCards
                  ? storageCards.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                label={translate('donauStorageIncApp.storageCardTraffic.type')}
                id="storage-card-traffic-type"
                name="type"
                data-cy="type"
                type="select"
                disabled={!isNew}
              >
                {storageCardTrafficTypeValues.map(storageCardTrafficType => (
                  <option value={storageCardTrafficType} key={storageCardTrafficType}>
                    {translate('donauStorageIncApp.StorageCardTrafficType.' + storageCardTrafficType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('donauStorageIncApp.storageCardTraffic.direction')}
                id="storage-card-traffic-direction"
                name="direction"
                data-cy="direction"
                type="select"
                disabled={!isNew}
              >
                {storageCardTrafficDirectionValues.map(storageCardTrafficDirection => (
                  <option value={storageCardTrafficDirection} key={storageCardTrafficDirection}>
                    {translate('donauStorageIncApp.StorageCardTrafficDirection.' + storageCardTrafficDirection)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('donauStorageIncApp.storageCardTraffic.amount')}
                id="storage-card-traffic-amount"
                name="amount"
                data-cy="amount"
                type="text"
                onChange={e => setAmount(parseFloat(e.target.value))}
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
                disabled={!isNew}
              />
              <ValidatedField
                label={translate('donauStorageIncApp.storageCardTraffic.price')}
                id="storage-card-traffic-price"
                name="price"
                data-cy="price"
                type="text"
                onChange={e => setPrice(parseFloat(e.target.value))}
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
                disabled={!isNew}
              />
              <ValidatedField
                label={translate('donauStorageIncApp.storageCardTraffic.trafficValue')}
                id="storage-card-traffic-trafficValue"
                name="trafficValue"
                data-cy="trafficValue"
                type="text"
                value={trafficValue}
                validate={{
                  //required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
                disabled
              />
              <UncontrolledTooltip target="trafficValueLabel">
                <Translate contentKey="donauStorageIncApp.storageCardTraffic.help.trafficValue" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('donauStorageIncApp.storageCardTraffic.document')}
                id="storage-card-traffic-document"
                name="document"
                data-cy="document"
                type="text"
                disabled={!isNew}
              />
              <ValidatedField
                label={translate('donauStorageIncApp.storageCardTraffic.date')}
                id="storage-card-traffic-date"
                name="date"
                data-cy="date"
                type="date"
                disabled={!isNew}
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

export default StorageCardTrafficUpdate;
