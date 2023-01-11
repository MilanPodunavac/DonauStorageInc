import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { IResource } from 'app/shared/model/resource.model';
import { IBusinessPartner } from 'app/shared/model/business-partner.model';
import { IBusinessYear } from 'app/shared/model/business-year.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface ICompany {
  id?: number;
  legalEntityInfo?: ILegalEntity;
  resources?: IResource[] | null;
  businessPartners?: IBusinessPartner[] | null;
  businessYears?: IBusinessYear[] | null;
  employees?: IEmployee[] | null;
}

export const defaultValue: Readonly<ICompany> = {};
