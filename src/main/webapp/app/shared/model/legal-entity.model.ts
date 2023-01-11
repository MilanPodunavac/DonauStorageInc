import { IContactInfo } from 'app/shared/model/contact-info.model';

export interface ILegalEntity {
  id?: number;
  name?: string;
  taxIdentificationNumber?: string;
  identificationNumber?: string;
  contactInfo?: IContactInfo;
}

export const defaultValue: Readonly<ILegalEntity> = {};
