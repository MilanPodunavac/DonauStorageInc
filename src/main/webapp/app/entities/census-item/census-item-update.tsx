import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICensusDocument } from 'app/shared/model/census-document.model';
import { getEntities as getCensusDocuments } from 'app/entities/census-document/census-document.reducer';
import { IResource } from 'app/shared/model/resource.model';
import { getEntities as getResources } from 'app/entities/resource/resource.reducer';
import { ICensusItem } from 'app/shared/model/census-item.model';
import { getEntity, updateEntity, createEntity, reset } from './census-item.reducer';

export const CensusItemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;
  const { censusId } = useParams<'censusId'>();
  const isWithCensus = censusId === 'undefined';

  const censusDocuments = useAppSelector(state => state.censusDocument.entities);
  const resources = useAppSelector(state => state.resource.entities);
  const censusItemEntity = useAppSelector(state => state.censusItem.entity);
  const loading = useAppSelector(state => state.censusItem.loading);
  const updating = useAppSelector(state => state.censusItem.updating);
  const updateSuccess = useAppSelector(state => state.censusItem.updateSuccess);

  const chosenBusinessYear = useAppSelector(state => state.locale.businessYear);

  const handleClose = () => {
    navigate(-1);
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCensusDocuments({}));
    dispatch(getResources({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...censusItemEntity,
      ...values,
      censusDocument: censusDocuments.find(it => it.id.toString() === values.censusDocument.toString()),
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
          censusDocument: censusId,
        }
      : {
          ...censusItemEntity,
          censusDocument: censusItemEntity?.censusDocument?.id,
          resource: censusItemEntity?.resource?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="donauStorageIncApp.censusItem.home.createOrEditLabel" data-cy="CensusItemCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.censusItem.home.createOrEditLabel">Create or edit a CensusItem</Translate>
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
                  id="census-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                  disabled
                />
              ) : null}
              <ValidatedField
                id="census-item-censusDocument"
                name="censusDocument"
                data-cy="censusDocument"
                label={translate('donauStorageIncApp.censusItem.censusDocument')}
                type="select"
                required
                disabled={isNew && !isWithCensus}
              >
                <option value="" key="0" />
                {censusDocuments
                  ? censusDocuments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.storage.name + ' (' + otherEntity.businessYear.yearCode + ')'}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                label={translate('donauStorageIncApp.censusItem.amount')}
                id="census-item-amount"
                name="amount"
                data-cy="amount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="census-item-resource"
                name="resource"
                data-cy="resource"
                label={translate('donauStorageIncApp.censusItem.resource')}
                type="select"
                required
              >
                <option value="" key="0" />
                {resources
                  ? resources
                      .filter(e => e.company.id === chosenBusinessYear.company.id)
                      .map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
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

export default CensusItemUpdate;
