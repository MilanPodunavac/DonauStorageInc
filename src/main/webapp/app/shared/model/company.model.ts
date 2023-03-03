import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { IResource } from 'app/shared/model/resource.model';
import { IBusinessPartner } from 'app/shared/model/business-partner.model';
import { IBusinessYear } from 'app/shared/model/business-year.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IStorage } from 'app/shared/model/storage.model';

export interface ICompany {
  id?: number;
  legalEntityInfo?: ILegalEntity;
  resources?: IResource[] | null;
  partners?: IBusinessPartner[] | null;
  businessYears?: IBusinessYear[] | null;
  employees?: IEmployee[] | null;
  storages?: IStorage[] | null;
}

export const defaultValue: Readonly<ICompany> = {};
