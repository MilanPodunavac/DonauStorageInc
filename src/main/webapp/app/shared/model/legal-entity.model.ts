import { IContactInfo } from 'app/shared/model/contact-info.model';
import { IAddress } from 'app/shared/model/address.model';
import { IBusinessPartner } from 'app/shared/model/business-partner.model';
import { ICompany } from 'app/shared/model/company.model';

export interface ILegalEntity {
  id?: number;
  name?: string;
  taxIdentificationNumber?: string;
  identificationNumber?: string;
  contactInfo?: IContactInfo;
  address?: IAddress;
  businessPartner?: IBusinessPartner | null;
  company?: ICompany | null;
}

export const defaultValue: Readonly<ILegalEntity> = {};
