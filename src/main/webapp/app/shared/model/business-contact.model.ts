import { IPerson } from 'app/shared/model/person.model';
import { IBusinessPartner } from 'app/shared/model/business-partner.model';

export interface IBusinessContact {
  id?: number;
  personalInfo?: IPerson;
  businessPartner?: IBusinessPartner | null;
}

export const defaultValue: Readonly<IBusinessContact> = {};
