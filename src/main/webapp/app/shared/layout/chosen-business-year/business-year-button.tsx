import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from 'reactstrap';

const BusinessYearButton = props => {
  return (
    <Button tag={Link} to={`/business-year/${props.businessYear.id}`} size="sm" className="d-flex align-items-center">
      {props.businessYear.yearCode + ' (' + (props.businessYear.completed ? 'COMPLETED' : 'INCOMPLETE') + ')'}
    </Button>
  );
};

export default BusinessYearButton;
