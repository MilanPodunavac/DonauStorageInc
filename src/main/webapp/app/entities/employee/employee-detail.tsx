import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee.reducer';

export const EmployeeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeEntity = useAppSelector(state => state.employee.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeDetailsHeading">
          <Translate contentKey="donauStorageIncApp.employee.detail.title">Employee</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.id}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.person.fullName">Name</Translate>
          </dt>
          <dd>
            {employeeEntity.personalInfo
              ? employeeEntity.personalInfo.firstName +
                (employeeEntity.personalInfo.middleName ? ' ' + employeeEntity.personalInfo.middleName : '') +
                ' ' +
                employeeEntity.personalInfo.lastName +
                (employeeEntity.personalInfo.maidenName ? ' (' + employeeEntity.personalInfo.maidenName + ')' : '')
              : ''}
          </dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.person.gender">Gender</Translate>
          </dt>
          <dd>{employeeEntity.personalInfo ? employeeEntity.personalInfo.gender : ''}</dd>
          <dt>
            <span id="uniqueIdentificationNumber">
              <Translate contentKey="donauStorageIncApp.employee.uniqueIdentificationNumber">Unique Identification Number</Translate>
            </span>
            <UncontrolledTooltip target="uniqueIdentificationNumber">
              <Translate contentKey="donauStorageIncApp.employee.help.uniqueIdentificationNumber" />
            </UncontrolledTooltip>
          </dt>
          <dd>{employeeEntity.uniqueIdentificationNumber}</dd>
          <dt>
            <span id="birthDate">
              <Translate contentKey="donauStorageIncApp.employee.birthDate">Birth Date</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.birthDate ? <TextFormat value={employeeEntity.birthDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.contactInfo.email">Email</Translate>
          </dt>
          <dd>{employeeEntity.personalInfo ? employeeEntity.personalInfo.contactInfo.email : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.contactInfo.phoneNumber">Phone Number</Translate>
          </dt>
          <dd>{employeeEntity.personalInfo ? employeeEntity.personalInfo.contactInfo.phoneNumber : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.employee.address">Address</Translate>
          </dt>
          <dd>
            {employeeEntity.address
              ? employeeEntity.address.streetName +
                ' ' +
                employeeEntity.address.streetCode +
                ', ' +
                employeeEntity.address.city.name +
                ', ' +
                employeeEntity.address.postalCode
              : ''}
          </dd>
          <dt>
            <span id="disability">
              <Translate contentKey="donauStorageIncApp.employee.disability">Disability</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.disability ? 'true' : 'false'}</dd>
          <dt>
            <span id="employment">
              <Translate contentKey="donauStorageIncApp.employee.employment">Employment</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.employment ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.employee.company">Company</Translate>
          </dt>
          <dd>{employeeEntity.company ? employeeEntity.legalEntityInfo.name : ''}</dd>
          <dt>
            <Translate contentKey="donauStorageIncApp.employee.user">User</Translate>
          </dt>
          <dd>{employeeEntity.user ? employeeEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeDetail;
