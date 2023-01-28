import { IContactInfo } from 'app/shared/model/contact-info.model';
import { IAddress } from 'app/shared/model/address.model';

export interface ILegalEntity {
  id?: number;
  name?: string;
  taxIdentificationNumber?: string;
  identificationNumber?: string;
  contactInfo?: IContactInfo;
  address?: IAddress;
}

export const defaultValue: Readonly<ILegalEntity> = {};
