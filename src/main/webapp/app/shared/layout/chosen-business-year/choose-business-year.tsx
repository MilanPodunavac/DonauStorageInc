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
import { setBusinessYear } from 'app/shared/reducers/locale';

export const ChooseBusinessYear = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const businessYears = useAppSelector(state => state.businessYear.entities);
  const loading = useAppSelector(state => state.transferDocument.loading);

  const handleClose = () => {
    navigate('/' + location.search);
  };

  useEffect(() => {
    dispatch(getBusinessYears({}));
  }, []);

  const defaultValues = () =>
    loading
      ? {}
      : {
          businessYear: businessYears.find(it => it.id.toString() === '1'),
        };

  const changeYear = values => {
    const businessYear = businessYears.find(it => it.id.toString() === values.businessYear.toString());

    dispatch(setBusinessYear(businessYear));
    handleClose();
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="donauStorageIncApp.transferDocument.home.createOrEditLabel" data-cy="TransferDocumentCreateUpdateHeading">
            <Translate contentKey="donauStorageIncApp.businessYear.home.choose">Choose a Business Year</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="5">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={changeYear}>
              <ValidatedField
                id="choose-businessYear"
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
                        {otherEntity.yearCode + ' - ' + otherEntity.company.legalEntityInfo.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>

              <Button color="primary" id="choose-year" data-cy="chooseBusinessYearButton" type="submit">
                <FontAwesomeIcon icon="asterisk" />
                &nbsp;
                <Translate contentKey="entity.action.choose">Choose</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ChooseBusinessYear;
