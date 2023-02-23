import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from 'reactstrap';

const ChooseBusinessYearButton = props => {
  return (
    <Button tag={Link} to={`/choose-business-year`} size="sm" className="d-flex align-items-center" color="primary">
      {props.businessYear.id != 0 ? 'Change' : 'No business year chosen'}
    </Button>
  );
};

export default ChooseBusinessYearButton;
